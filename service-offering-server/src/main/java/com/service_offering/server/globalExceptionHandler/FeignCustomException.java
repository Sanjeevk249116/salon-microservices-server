package com.service_offering.server.globalExceptionHandler;

public class FeignCustomException extends RuntimeException {
    private final int statusCode;

    public FeignCustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
