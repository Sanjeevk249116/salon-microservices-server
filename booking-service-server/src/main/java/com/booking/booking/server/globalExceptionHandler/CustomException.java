package com.booking.booking.server.globalExceptionHandler;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
