package todo.entity;

import db.Entity;

import java.util.Date;

public class Step extends Entity {

    public enum Status  {
        NotStarted , Completed
    }
    public String title;
    public Status status;
    public int taskRef;
    public static final int STEP_ENTITY_CODE = 17;


    public Step (String title , int taskRef, Status status){
        this.title = title;
        this.taskRef = taskRef;
        this.status = status;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }

    public Step copy() {
        Step stepCopy = new Step( title, taskRef, status);
        stepCopy.id = id;
        stepCopy.taskRef = taskRef;
        stepCopy.title = title;
        return stepCopy;
    }



}
