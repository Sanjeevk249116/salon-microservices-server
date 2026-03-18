package com.salon.salon.server.controller;

import com.salon.salon.server.dto.SalonDto;
import com.salon.salon.server.entity.Salon;
import com.salon.salon.server.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
