package com.booking.booking.server.globalExceptionHandler;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

public class UserNotFoundException extends OAuth2AuthenticationException {

    private final int code;

    public UserNotFoundException(String message, int code) {
        super(new OAuth2Error("user_not_found", message, null));
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}