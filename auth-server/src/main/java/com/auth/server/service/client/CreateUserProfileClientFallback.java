package com.auth.server.service.client;

import com.auth.server.dto.clientDto.UserRequestDto;
import com.auth.server.dto.clientDto.UserResponseClientDto;
import com.auth.server.exceptionHandling.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProfileClientFallback implements CreateUserProfileClient {
    @Override
    public ResponseEntity<UserResponseClientDto> createUserProfile(UserRequestDto newUser, String authHeader) {
        System.out.println("User-service is unavailable. Failed to create profile for: {}" + newUser.getEmail());
        // You can throw a custom exception or return a default response
        throw new RuntimeException("User Profile Service is currently unavailable");
    }
}
