package todo.service;


import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StepService {
   public static void addStep( int taskRef, String title){
       Step newStep = new Step(title, taskRef, Step.Status.NotStarted);

       try {
           Database.add(newStep);

           Date creationDate = new Date();
           SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
           dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

           System.out.println("Step saved successfully.");
           System.out.println("ID : " + newStep.id);
           System.out.println("Creation Date: " + dateFormat.format(creationDate));
       } catch (InvalidEntityException e) {
           System.out.println("Cannot save step.");
           System.out.println("Error: " + e.getMessage());
       }

   }

   public static void delStep(int id){
       try {
           Database.delete(id);
           System.out.println("Entity with ID = " + id + " successfully deleted.");
       } catch (EntityNotFoundException e) {
           System.out.println(e.getMessage());
           System.out.println("Error : something happened.");
       }
   }

   public static void uptStep (int id, String field, String newValue){
       Step step = (Step) Database.get(id);
       Step uptStep = null;
       String oldValue;

       if (field.equals("title")) {
           uptStep = new Step(newValue, step.taskRef, step.status);
           oldValue = step.title;

       } else if (field.equals("taskRef")) {
           uptStep = new Step(step.title,  Integer.parseInt(newValue), step.status);
           oldValue = Integer.toString(step.taskRef);

       } else {
           if (newValue.equals("not started")) {
               uptStep = new Step(step.title, step.taskRef,Step.Status.NotStarted);

           } else {
               uptStep = new Step(step.title, step.taskRef, Step.Status.Completed);

               int flagCompleted = 1;
               for (Entity entity : Database.getAll(Step.STEP_ENTITY_CODE)) {
                   if (((Step) entity).taskRef == step.taskRef) {
                       if (((Step) entity).status != Step.Status.Completed) {
                           flagCompleted = 0;
                           break;
                       }
                   }
               }

               if (flagCompleted == 1)
                   TaskService.uptT(step.id, "status", "completed");
               else
                   TaskService.uptT(step.id, "status", "in progress");
           }

           oldValue = step.status.toString();
       }

   }

}

