package com.booking.booking.server.service.client;

import com.booking.booking.server.config.FeignConfig;
import com.booking.booking.server.dto.ServiceOfferingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Set;

@FeignClient(name = "SERVICE-OFFERING-SERVER", path = "/api/service-offer", configuration = FeignConfig.class,fallback = ServiceOfferingClientFallBack.class)
public interface ServiceOfferingClient {

    @PostMapping("/read/all-service-by-ids")
    ResponseEntity<Set<ServiceOfferingResponseDto>> readAllServiceOfferByIds(@RequestBody Set<Long> ids, @RequestHeader("Authorization") String authHeader);
}
