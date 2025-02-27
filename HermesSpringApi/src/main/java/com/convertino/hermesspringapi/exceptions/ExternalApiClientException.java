package com.convertino.hermesspringapi.exceptions;

public class ExternalApiClientException extends RuntimeException {
    public ExternalApiClientException(String message) {
        super(message);
    }

    static public ExternalApiClientException run(String service, String msg) {
        return new ExternalApiClientException("There was an error while trying to call " + service + ": " + msg);
    }
}
