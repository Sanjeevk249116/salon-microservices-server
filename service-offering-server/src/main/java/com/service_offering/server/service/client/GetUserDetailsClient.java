package com.service_offering.server.service.client;


import com.service_offering.server.config.FeignConfig;
import com.service_offering.server.dto.UserResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USERS-MICRO-SERVER", path = "/api/user",fallback = GetUserDetailClientFallBack.class,configuration = FeignConfig.class)
public interface GetUserDetailsClient {

    @GetMapping("/read/user-profile")
    ResponseEntity<UserResponseClientDto> getUserProfile(@RequestHeader("Authorization") String authHeader);

}
