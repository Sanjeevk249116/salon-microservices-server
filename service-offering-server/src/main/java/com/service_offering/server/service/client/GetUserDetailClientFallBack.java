package com.service_offering.server.service.client;


import com.service_offering.server.dto.UserResponseClientDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public class GetUserDetailClientFallBack implements GetUserDetailsClient {
    @Override
    public ResponseEntity<UserResponseClientDto> getUserProfile(@RequestHeader("Authorization") String authHeader) {

        throw new RuntimeException("User Profile Service is currently unavailable");

    }
}
