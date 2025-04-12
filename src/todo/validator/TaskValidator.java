package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
         if (entity.copy().getEntityCode() == 16){
             Task task = (Task) entity;
             if (task.title == null){
                 throw new InvalidEntityException("title must not empty");

             }
         }
         else {
             throw new IllegalArgumentException("This class is not a subclass of task.");
         }
    }
}
