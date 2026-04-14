package com.booking.booking.server.service.client;

import com.booking.booking.server.dto.ServiceOfferingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ServiceOfferingClientFallBack implements ServiceOfferingClient{
    @Override
    public ResponseEntity<Set<ServiceOfferingResponseDto>> readAllServiceOfferByIds(Set<Long> ids, String authHeader) {
        return null;
    }
}
