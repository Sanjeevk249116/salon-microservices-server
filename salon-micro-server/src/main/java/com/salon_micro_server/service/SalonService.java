package com.salon_micro_server.service;


import com.salon_micro_server.dto.SalonDto;
import com.salon_micro_server.dto.SalonResponseDto;
import com.salon_micro_server.entity.Salon;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalonService {
    Salon createNewSalon(@Valid SalonDto newSalonData, Long userId);

    Salon updateSalon(SalonDto updateSalonData, Long salonId,Long ownerId);

    List<Salon> getAllSalonList();

    SalonResponseDto readSingleSalonById(Long salonId);

    String deleteSalonAccount(Long salonId, Long id);

    List<Salon> searchSalon(String keyword);

    List<SalonResponseDto> getMySalonDetail(Long userId);
}
