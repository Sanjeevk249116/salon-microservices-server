package com.service_offering.server.controller;

import com.service_offering.server.dto.*;
import com.service_offering.server.service.ServiceOfferingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering")
public class ServiceOfferingSalonController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/create-service-offering")
    public ResponseEntity<ServiceOfferingResponseDto> createServiceOffering(@RequestBody @Valid ServiceOfferingDto newServiceOfferingData) {
        SalonDto salon = new SalonDto();
        salon.setId(1L);

        CategoryDto category = new CategoryDto();
        category.setId(1L);

        UserDto user = new UserDto();
        user.setId(1L);

        ServiceOfferingResponseDto newServiceOffering = serviceOfferingService.createNewServiceOfferingByOwner(newServiceOfferingData, salon.getId(), category.getId(), user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOffering);
    }


    @PutMapping("/update-service-offering/{serviceOfferingId}")
    public ResponseEntity<ServiceOfferingResponseDto> updateServiceOffering(@RequestBody @Valid ServiceOfferingDto newServiceOfferingData, @PathVariable Long serviceOfferingId) {

        UserDto user = new UserDto();
        user.setId(1L);

        ServiceOfferingResponseDto newServiceOffering = serviceOfferingService.updateServiceOfferingByOwner(newServiceOfferingData, serviceOfferingId, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOffering);
    }

}
