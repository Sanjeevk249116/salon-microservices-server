package com.salon_micro_server.globalExceptionHandler;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
