package com.convertino.hermesspringapi.exceptions;

public class EntityCreationException extends RuntimeException {
    public EntityCreationException(String message) {
        super(message);
    }

    static public EntityCreationException run(String objectName) {
        return new EntityCreationException("There was an error while trying to create this " + objectName);
    }
}
