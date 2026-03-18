package com.salon.salon.server.service;

import com.salon.salon.server.dto.SalonDto;
import com.salon.salon.server.entity.Salon;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface SalonService {
    Salon createNewSalon(@Valid SalonDto newSalonData);
}
