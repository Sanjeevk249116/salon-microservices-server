package com.users_micro_server.exceptionHandling;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    private int code;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, int status) {
        super(message);
        this.code = status;
    }


}
