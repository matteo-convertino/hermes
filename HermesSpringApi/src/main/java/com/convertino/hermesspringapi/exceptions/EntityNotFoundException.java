package com.convertino.hermesspringapi.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    static public EntityNotFoundException run(String objectName, String propertyName, String value) {
        return new EntityNotFoundException("No " + objectName + " with " + propertyName + " = " + value + " found");
    }

    static public EntityNotFoundException run(String objectName, String propertyName, Long value) {
        return EntityNotFoundException.run(objectName,  propertyName, value.toString());
    }

    static public EntityNotFoundException run(String objectName, String propertyName, int value) {
        return EntityNotFoundException.run(objectName,  propertyName, String.valueOf(value));
    }
}
