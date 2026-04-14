package com.service_offering.server.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.service_offering.server.dto.ErrorResponse;
import com.service_offering.server.globalExceptionHandler.FeignCustomException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.InputStream;


public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();


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
            System.err.println("??????????????????????????????"+e);
            return new RuntimeException("Unknown error occurred");
        }
    }
}
