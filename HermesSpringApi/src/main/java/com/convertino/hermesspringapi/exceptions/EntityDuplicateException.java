package com.convertino.hermesspringapi.exceptions;

public class EntityDuplicateException extends RuntimeException {
    public EntityDuplicateException(String message) {
        super(message);
    }

    static public EntityDuplicateException run(String objectName, String propertyName, String value) {
        return new EntityDuplicateException("There is already a " + objectName + " with " + propertyName + " = " + value);
    }

    static public EntityDuplicateException run(String objectName, String propertyName, Long value) {
        return EntityDuplicateException.run(objectName, propertyName, value.toString());
    }

    static public EntityDuplicateException run(String objectName, String propertyName, Integer value) {
        return EntityDuplicateException.run(objectName, propertyName, value.toString());
    }
}