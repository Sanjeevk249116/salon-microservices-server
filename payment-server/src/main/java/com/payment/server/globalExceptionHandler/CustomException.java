package com.payment.server.globalExceptionHandler;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
