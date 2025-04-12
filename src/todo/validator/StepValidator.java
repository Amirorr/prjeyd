package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if(entity.copy().getEntityCode() == 17){
            Step step = (Step) entity;
            if(step.title == null){
                throw new InvalidEntityException("title must not empty");
            }
            Database.get(step.taskRef);
        }
        else {
            throw new IllegalArgumentException("This class is not a subclass of step.");
        }
    }
}
