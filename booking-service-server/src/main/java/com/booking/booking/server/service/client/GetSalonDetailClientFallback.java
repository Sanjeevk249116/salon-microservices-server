package com.booking.booking.server.service.client;



import com.booking.booking.server.dto.SalonResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetSalonDetailClientFallback implements GetSalonDetailClient{

    @Override
    public ResponseEntity<SalonResponseDto> readSingleSalon(Long salonId) {
        return null;
    }
}
