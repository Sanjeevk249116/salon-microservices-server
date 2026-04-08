package com.auth.server.service.client;

import com.auth.server.dto.clientDto.UserRequestDto;
import com.auth.server.dto.clientDto.UserResponseClientDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USERS-MICRO-SERVER", path = "/api/user",fallback = CreateUserProfileClientFallback.class)
public interface CreateUserProfileClient {

    @PostMapping("/create")
    ResponseEntity<UserResponseClientDto> createUserProfile(@RequestBody @Valid UserRequestDto newUser, @RequestHeader("Authorization") String authHeader);
}
