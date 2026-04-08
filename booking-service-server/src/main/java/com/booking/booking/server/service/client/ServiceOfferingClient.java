package com.booking.booking.server.service.client;

import com.booking.booking.server.dto.ServiceOfferingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(value = "BOOKING-SERVICE-SERVER", path = "/api/booking-service")
public interface ServiceOfferingClient {

    @GetMapping("/read/all-service-by-ids")
    ResponseEntity<Set<ServiceOfferingResponseDto>> readAllServiceOfferByIds(@RequestBody Set<Long> ids);
}
