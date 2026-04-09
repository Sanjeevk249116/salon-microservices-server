package com.booking.booking.server.globalExceptionHandler;

public class FeignCustomException extends RuntimeException {
  public FeignCustomException(String message) {
    super(message);
  }
}
