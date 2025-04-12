package todo.entity;

import db.Trackable;
import db.*;

import javax.management.relation.RoleStatus;
import java.io.ObjectInputFilter;
import java.util.Date;

public class Task extends Entity implements Trackable {
    public String title;
    public String description;
    public String dueDate;
    public static final int TASK_ENTITY_CODE = 16;
    private Date creationDate ;
    private Date lastModificationDate;
    public Status status;

    public enum Status {
        NotStarted , InProgress , Completed
    }

    public Task (String title, String description, String dueDate, Status status){
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    public Task copy() {
        Task taskCopy = new Task(title, description, dueDate, status);
        taskCopy.title = title;
        taskCopy.description = description;
        taskCopy.dueDate = dueDate;
        taskCopy.id = id;
        taskCopy.status = status;
        return taskCopy;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
