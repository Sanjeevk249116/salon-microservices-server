package com.booking.booking.server.service.client;


import com.booking.booking.server.config.FeignConfig;
import com.booking.booking.server.dto.SalonResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SALON-MICRO-SERVER",
        configuration = FeignConfig.class,
        path = "/api/salon",
        fallback = GetSalonDetailClientFallback.class)
public interface GetSalonDetailClient {

    @GetMapping("/read-single-salon/{salonId}")
    ResponseEntity<SalonResponseDto> readSingleSalon(@PathVariable Long salonId,
                                                     @RequestHeader("Authorization") String authHeader);
}
