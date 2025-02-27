package com.convertino.hermesspringapi.exceptions;

public class EntityDeletionException extends RuntimeException {
    public EntityDeletionException(String message) {
        super(message);
    }

    static public EntityDeletionException run(String objectName, String propertyName, String value) {
        return new EntityDeletionException("There was an error while trying to delete this " + objectName + " with " + propertyName + " = " + value);
    }

    static public EntityDeletionException run(String objectName, String propertyName, Long value) {
        return EntityDeletionException.run(objectName, propertyName, value.toString());
    }

    static public EntityDeletionException run(String objectName, String propertyName, Integer value) {
        return EntityDeletionException.run(objectName, propertyName, value.toString());
    }
}
