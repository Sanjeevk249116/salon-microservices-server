package com.users_micro_server.exceptionHandling;

import com.users_micro_server.payloadResponse.ApiResponse;
import jakarta.annotation.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalApiResponseStructure implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof ApiResponse) return body;
        int statusCode = 200;
        if (response instanceof org.springframework.http.server.ServletServerHttpResponse servletResponse) {
            statusCode = servletResponse.getServletResponse().getStatus();
        }

        HttpStatus status = HttpStatus.resolve(statusCode);
        if (status == HttpStatus.NO_CONTENT) return null;

        return new ApiResponse<>(
                status != null ? status.value() : 200,
                status != null && status.is2xxSuccessful(),
                status != null && status.is2xxSuccessful() ? "Success" : "Error",
                body
        );
    }
}
