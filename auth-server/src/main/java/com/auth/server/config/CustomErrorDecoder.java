package com.auth.server.config;

import com.auth.server.dto.clientDto.ErrorResponse;
import com.auth.server.exceptionHandling.FeignCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.InputStream;


public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper=new ObjectMapper();


    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {

            ErrorResponse errorResponse =
                    objectMapper.readValue(bodyIs, ErrorResponse.class);

            return new FeignCustomException(
                    errorResponse.getMessage(),
                    errorResponse.getStatusCode()
            );

        } catch (Exception e) {
            return new RuntimeException("Unknown error occurred");
        }
    }
}
