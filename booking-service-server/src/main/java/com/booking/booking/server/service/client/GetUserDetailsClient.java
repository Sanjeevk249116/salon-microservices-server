package com.booking.booking.server.service.client;


import com.booking.booking.server.config.FeignConfig;
import com.booking.booking.server.dto.UserResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USERS-MICRO-SERVER", path = "/api/user",fallback = GetUserDetailClientFallBack.class,configuration = FeignConfig.class)
public interface GetUserDetailsClient {

    @GetMapping("/read/user-profile")
    ResponseEntity<UserResponseClientDto> getUserProfile(@RequestHeader("Authorization") String authHeader);

}
