package com.booking.booking.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private Object data;
    private String message;
    private int statusCode;
    private boolean status;
}