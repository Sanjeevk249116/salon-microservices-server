package com.service_offering.server.service.client;



import com.service_offering.server.config.FeignConfig;
import com.service_offering.server.dto.SalonResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "SALON-MICRO-SERVER", path = "/api/salon",fallback = GetSalonDetailClientFallback.class,configuration = FeignConfig.class)
public interface GetSalonDetailClient {

    @GetMapping("/owner/read/my-salon")
    @PreAuthorize("hasRole('OWNER')")
    ResponseEntity<List<SalonResponseDto>> getMySalonDetails(@RequestHeader("Authorization") String token);

}
