package com.salon_micro_server.service.client;

import com.salon_micro_server.dto.UserResponseClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public class GetUserDetailClientFallBack implements GetUserDetailsClient {
    @Override
    public ResponseEntity<UserResponseClientDto> getUserProfile(@RequestHeader("Authorization") String authHeader) {

        throw new RuntimeException("User Profile Service is currently unavailable");

    }
}
