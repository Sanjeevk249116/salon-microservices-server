package com.salon_micro_server.service.client;

import com.salon_micro_server.dto.UserResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USERS-MICRO-SERVER", path = "/api/user",fallback = GetUserDetailClientFallBack.class)
public interface GetUserDetailsClient {

    @GetMapping("/read/user-profile")
    ResponseEntity<UserResponseClientDto> getUserProfile(@RequestHeader("Authorization") String authHeader);

}
