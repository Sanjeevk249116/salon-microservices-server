package com.salon.salon.server.controller;

import com.salon.salon.server.dto.SalonDto;
import com.salon.salon.server.entity.Salon;
import com.salon.salon.server.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salon")
public class SalonController {

    private final SalonService salonService;

    @PostMapping("/create-new-salon")
    public ResponseEntity<Salon> createSalon(@RequestBody @Valid SalonDto newSalonData) {
        Salon newSalon = salonService.createNewSalon(newSalonData);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSalon);
    }

    @PutMapping("/update-salon/{salonId}")
    public ResponseEntity<Salon> updateSalonDetail(@RequestBody SalonDto updateSalonData, @PathVariable Long salonId){
        Salon updatedSalon =salonService.updateSalon(updateSalonData,salonId);
        return ResponseEntity.status(200).body(updatedSalon);
    }


}
