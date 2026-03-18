package com.salon.salon.server.globalExceptionHandler;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
