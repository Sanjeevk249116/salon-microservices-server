package com.salon.salon.server.controller;

import com.salon.salon.server.dto.SalonDto;
import com.salon.salon.server.dto.UserDto;
import com.salon.salon.server.entity.Salon;
import com.salon.salon.server.service.SalonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salon")
public class SalonController {

    private final SalonService salonService;

    @PostMapping("/create-new-salon")
    public ResponseEntity<Salon> createSalon(@RequestBody @Valid SalonDto newSalonData) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        Salon newSalon = salonService.createNewSalon(newSalonData, userDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newSalon);
    }

    @PutMapping("/update-salon/{salonId}")
    public ResponseEntity<Salon> updateSalonDetail(@RequestBody SalonDto updateSalonData, @PathVariable Long salonId) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        Salon updatedSalon = salonService.updateSalon(updateSalonData, salonId, userDto.getId());
        return ResponseEntity.status(200).body(updatedSalon);
    }

    @GetMapping("/salon-list")
    public ResponseEntity<List<Salon>> getAllSalonList() {
        List<Salon> salonList = salonService.getAllSalonList();
        return ResponseEntity.status(200).body(salonList);
    }

    @GetMapping("/read-single-salon/{salonId}")
    public ResponseEntity<Salon> readSingleSalon(@PathVariable Long salonId) {
        Salon salon = salonService.readSingleSalonById(salonId);
        return ResponseEntity.ok(salon);
    }

    @DeleteMapping("/delete-salon/{salonId}")
    public ResponseEntity<String> deleteSalonAccount(@PathVariable Long salonId) {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        String str = salonService.deleteSalonAccount(salonId,userDto.getId());
        return ResponseEntity.ok(str);
    }

    @GetMapping("/search-salon")
    public ResponseEntity<List<Salon>> searchSalon(@RequestParam(value="keyword",required = false) String keyword){
        List<Salon> salon =salonService.searchSalon(keyword);
        return ResponseEntity.ok(salon);
    }

}
