package com.users_micro_server.exceptionHandling;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
