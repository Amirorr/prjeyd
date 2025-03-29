package db;

import db.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class Database {
    private static  ArrayList<Entity> entities = new ArrayList<>();

    private Database() { }

    public static void add(Entity e) {
        e.id = generateId();
        entities.add(e);
    }

    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities){
            if(id == entity.id){
                return entity;
            }
        }
        throw new EntityNotFoundException(id);

    }

    public static void delete(int id) throws EntityNotFoundException {
        Entity entity = get(id);
        entities.remove(entity);
    }

    public static void update(Entity e) throws EntityNotFoundException {
        int index = entities.indexOf(get(e.id));
        entities.set(index, e);
    }

    private static int generateId() {
        return entities.size() + 1;
    }
}
