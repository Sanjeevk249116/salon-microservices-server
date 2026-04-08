package com.service_offering.server.service.client;


import com.service_offering.server.dto.SalonResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetSalonDetailClientFallback implements GetSalonDetailClient{
    @Override
    public ResponseEntity<List<SalonResponseDto>> getMySalonDetails(String token) {
        throw new RuntimeException("User Profile Service is currently unavailable");
    }
}
