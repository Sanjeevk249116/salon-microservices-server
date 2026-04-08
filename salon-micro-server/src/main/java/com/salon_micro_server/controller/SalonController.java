package com.salon_micro_server.controller;


import com.salon_micro_server.dto.SalonDto;
import com.salon_micro_server.dto.SalonResponseDto;
import com.salon_micro_server.dto.UserDto;
import com.salon_micro_server.entity.Salon;
import com.salon_micro_server.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salon")
public class SalonController {

    private final SalonService salonService;

    @PostMapping("/create-new-salon")
    public ResponseEntity<Salon> createSalon(@RequestBody @Valid SalonDto newSalonData, @AuthenticationPrincipal Jwt jwt) throws Exception {
        Salon newSalon = salonService.createNewSalon(newSalonData, jwt.getClaim("userId"));
        return ResponseEntity.status(HttpStatus.CREATED).body(newSalon);
    }

    @PutMapping("/owner/update-salon/{salonId}")
    public ResponseEntity<Salon> updateSalonDetail(@RequestBody SalonDto updateSalonData, @PathVariable Long salonId,@AuthenticationPrincipal Jwt jwt) throws Exception {
        Salon updatedSalon = salonService.updateSalon(updateSalonData, salonId, jwt.getClaim("userId"));
        return ResponseEntity.status(200).body(updatedSalon);
    }

    @GetMapping("/salon-list")
    public ResponseEntity<List<Salon>> getAllSalonList() {
        List<Salon> salonList = salonService.getAllSalonList();
        return ResponseEntity.status(200).body(salonList);
    }

    @GetMapping("/read-single-salon/{salonId}")
    public ResponseEntity<SalonResponseDto> readSingleSalon(@PathVariable Long salonId) {
        SalonResponseDto salon = salonService.readSingleSalonById(salonId);
        return ResponseEntity.ok(salon);
    }

    @DeleteMapping("/owner/delete-salon/{salonId}")
    public ResponseEntity<String> deleteSalonAccount(@PathVariable Long salonId,@AuthenticationPrincipal Jwt jwt) throws Exception {
        String str = salonService.deleteSalonAccount(salonId, jwt.getClaim("userId"));
        return ResponseEntity.ok(str);
    }

    @GetMapping("/owner/read/my-salon")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<SalonResponseDto>> getMySalonDetails(@AuthenticationPrincipal Jwt jwt) throws Exception {
        List<SalonResponseDto> salonResponseDto =salonService.getMySalonDetail(jwt.getClaim("userId"));
        return ResponseEntity.ok(salonResponseDto);
    }

    @GetMapping("/search-salon")
    public ResponseEntity<List<Salon>> searchSalon(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Salon> salon = salonService.searchSalon(keyword);
        return ResponseEntity.ok(salon);
    }



}
