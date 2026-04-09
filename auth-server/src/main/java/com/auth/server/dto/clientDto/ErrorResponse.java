package com.booking.booking.server.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private Object data;
    private String message;
    private int statusCode;
    private boolean status;
}