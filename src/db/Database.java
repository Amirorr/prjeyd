package db;

import db.exception.EntityNotFoundException;
import java.util.*;

import db.exception.InvalidEntityException;



public class Database {
    private static  ArrayList<Entity> entities = new ArrayList<>();
    private static HashMap<Integer, Validator> validators = new HashMap<>();
    private Database() { }

    public static void add(Entity e) throws InvalidEntityException {
        e.id = generateId();
        System.out.println(e.id);
        entities.add(e);


        if (e instanceof Trackable){
            Date now = new Date();
            ((Trackable) e).setCreationDate(now);
            ((Trackable) e).setLastModificationDate(now);
        }

    }




    public static void registerValidator(int entityCode, Validator validator) {
        for (Integer integer : validators.keySet()){
            if (integer == entityCode){
                throw new IllegalArgumentException("please enter the information correctly");
            }
        }
        validators.put(entityCode, validator);
    }
    public static Entity get(int id) throws EntityNotFoundException {
        for (Entity entity : entities){
            if(id == entity.copy().id){
                return entity.copy();
            }
        }
        throw new EntityNotFoundException(id);

    }

    public static void delete(int id) throws EntityNotFoundException {
        Entity entity = get(id);
        entities.remove(entity);
    }

    public static void update(Entity e) throws EntityNotFoundException, InvalidEntityException {


        if (e instanceof Trackable){
            Date  date = new Date();
            ((Trackable) e).setLastModificationDate(date);
        }
        else {
            int index = entities.indexOf(get(e.id));
            entities.set(index, e);
        }
    }

    private static int generateId() {
        return entities.size() + 1;
    }

    public static ArrayList<Entity> getAll(int entityCode) {
        ArrayList<Entity> list = new ArrayList<>();

