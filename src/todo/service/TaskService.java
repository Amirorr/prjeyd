package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskService {

        public static void addTask  (String title, String description, String dueDate) {
            Task newTask = new Task(title, description, dueDate, Task.Status.NotStarted);

            try {
                Database.add(newTask);
                System.out.println("Task saved successfully.");
                System.out.println("ID: " + newTask.id);
            } catch (InvalidEntityException e) {
                System.out.println("Cannot save task.");
                System.out.println("Error: " + e.getMessage());
            }

        }

        public static void delTask(int id){
            try {
                Database.delete(id);
                System.out.println("Entity with ID = " + id + " successfully deleted.");

                for (Entity entity : Database.getAll(Step.STEP_ENTITY_CODE)) {
                    if (((Step) entity).taskRef == id) {
                        Database.delete(((Step) entity).id);
                    }
                }

            } catch (EntityNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Error : something happened.");
            }
        }

        public static void uptT (int id, String field, String newValue){
            Task task = (Task) Database.get(id);
            Task updatedTask = null;
            String oldValue;

            if (field.equals("title")) {
                updatedTask = new Task(newValue, task.description, task.dueDate, task.status);
                oldValue = task.title;

            } else if (field.equals("description")) {
                updatedTask = new Task(task.title, newValue, task.dueDate, task.status);
                oldValue = task.description;

            } else if (field.equals("due date")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDueDate = LocalDate.parse(newValue, formatter);
                Date dueDate = Date.from(localDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                String newDue = dueDate.toString();

                updatedTask = new Task(task.title, task.description, newDue, task.status);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                oldValue = sdf.format(dueDate);

            } else {
                if (newValue.equals("not started")) {
                    updatedTask = new Task(task.title, task.description, task.dueDate, Task.Status.NotStarted);

                } else if (newValue.equals("in progress")) {
                    updatedTask = new Task(task.title, task.description, task.dueDate, Task.Status.InProgress);

                } else {
                    updatedTask = new Task(task.title, task.description, task.dueDate, Task.Status.Completed);

                    for (Entity entity : Database.getAll(Step.STEP_ENTITY_CODE)) {
                        if (((Step) entity).taskRef == task.id && ((Step) entity).status != Step.Status.Completed)
                            StepService.uptStep(entity.id, "status", "Completed");
                    }
                }

                oldValue = task.status.toString();
            }

            try {
                Database.update(updatedTask);

                Date motificationDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                System.out.println("Successfully updated the task.");
                System.out.println("Field: " + field);
                System.out.println("Old Value: " + oldValue);
                System.out.println("New Value: " + newValue);
                System.out.println("Modification Date: " + dateFormat.format(motificationDate));
            } catch (InvalidEntityException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Cannot update task with ID = " + id + ".");
                System.out.println("Error: Cannot find entity with id = " + id + ".");
            }
        }

        public static void getAllTask (){
            ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);
            tasks.sort(Comparator.comparing(task -> ((Task) task).dueDate));

            for (Entity entity : tasks) {
                showTaskAndSteps(entity);

                System.out.println();
            }


        }

        public static void showTaskAndSteps(Entity entity){
            Task task = (Task) entity;
            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.title);
            System.out.println("Due Date: " + task.dueDate);
            System.out.println("Status: " + task.status);

            for (Entity e : Database.getAll(Step.STEP_ENTITY_CODE)) {
                Step step = (Step) e;
                if (step.taskRef == task.id) {
                    System.out.println("    + " + step.title);
                    System.out.println("        ID: " + step.id);
                    System.out.println("        Status: " + step.status);
                }
            }
        }

        public static void incompleteTasks() {
        ArrayList<Entity> tasks = Database.getAll(Task.TASK_ENTITY_CODE);
        tasks.sort(Comparator.comparing(task -> ((Task) task).dueDate));

        for (Entity entity : tasks) {
            if (((Task) entity).status != Task.Status.Completed) {
                TaskService.showTaskAndSteps(entity);

                System.out.println();
            }
        }
    }




}

