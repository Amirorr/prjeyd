package example;

import db.*;
import db.Validator;
import db.exception.*;
import example.*;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {

        if(entity.copy().getEntityCode() == 14) {
            Human human = (Human) entity;
            if (human.copy().age <= 0 ) {
                throw new InvalidEntityException("Age must be a positive integer");
            }
            if (human.copy().name == null){
                throw new InvalidEntityException("the name must not be empty");
            }
        }
    }
}
