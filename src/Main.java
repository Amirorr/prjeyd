import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.*;
import todo.validator.StepValidator;
import todo.validator.TaskValidator;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Task.TASK_ENTITY_CODE, new TaskValidator());
        Database.registerValidator(Step.STEP_ENTITY_CODE, new StepValidator());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("1) add task \n" +
                    "2) Add Step \n" +
                    "3) Delete \n" +
                    "4) Update Task \n" +
                    "5) Update Step \n" +
                    "6) Get Task By Id \n" +
                    "7) Get All Tasks \n" +
                    "8) Get Incomplete Tasks \n" +
                    "9) Exit \n" +
                    "Enter command: ");

            String command = scanner.nextLine().trim().toLowerCase();

            try {
                if (command.equals("add task")) {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Description: ");
                    String description = scanner.nextLine();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.print("Due date (yyyy-MM-dd): ");
                    String inputDate = scanner.nextLine();

                    Date dueDate = null;
                    try {
                        LocalDate date = LocalDate.parse(inputDate, formatter);
                        dueDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                        continue;
                    }

                    TaskService.addTask(title,description, String.valueOf(dueDate));

                } else if (command.equals("add step")) {
                    System.out.print("TaskID: ");
                    int taskRef = scanner.nextInt();

                    try {
                        Entity entity = Database.get(taskRef);
                        if (!(entity instanceof Task))
                            throw new EntityNotFoundException(taskRef);
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.print("Title: ");
                    String title = scanner.nextLine();

                    StepService.addStep( taskRef,title);

                } else if (command.equals("delete")) {
                    System.out.print("ID: ");
                    int id = scanner.nextInt();

                    Entity entity = null;
                    try {
                        entity = Database.get(id);
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    if (entity instanceof Task)
                        TaskService.delTask(id);
                    else
                        StepService.delStep(id);

                } else if (command.equals("update task")) {
                    System.out.print("ID: ");
                    int id = scanner.nextInt();

                    Entity entity = null;
                    try {
                        entity = Database.get(id);
                        if (!(entity instanceof Task))
                            throw new EntityNotFoundException(id);

                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    System.out.print("Field: ");
                    String field = scanner.nextLine();

                    System.out.print("New Value: ");
                    String newValue = scanner.nextLine();

                    TaskService.uptT(id , field, newValue);

                } else if (command.equals("update step")) {
                    System.out.print("ID: ");
                    int id = scanner.nextInt();

                    Entity entity = null;
                    try {
                        entity = Database.get(id);
                        if (!(entity instanceof Step))
                            throw new EntityNotFoundException(id);

                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    System.out.print("Field: ");
                    String field = scanner.nextLine();

                    System.out.print("New Value: ");
                    String newValue = scanner.nextLine();

                    StepService.uptStep(id , field, newValue);

                } else if (command.equals("get task by id")) {
                    System.out.println("ID: ");
                    int id = scanner.nextInt();



                } else if (command.equals("get all tasks")) {
                    TaskService.getAllTask();

                } else if (command.equals("get incomplete tasks")) {
                    TaskService.incompleteTasks();

                } else if (command.equals("exit")) {
                    System.out.println("Exiting...");
                    break;

                } else {
                    throw new IllegalArgumentException("Unknown command. Please enter a valid command.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }
}