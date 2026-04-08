package com.service_offering.server.controller;

import com.service_offering.server.dto.*;
import com.service_offering.server.service.ServiceOfferingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner/service-offering")
public class ServiceOfferingSalonController {
    private final ServiceOfferingService serviceOfferingService;

    @PostMapping("/create-service-offering")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServiceOfferingResponseDto> createServiceOffering(@RequestBody @Valid ServiceOfferingDto newServiceOfferingData, @AuthenticationPrincipal Jwt jwt) throws  Exception {
        SalonDto salon = new SalonDto();
        salon.setId(1L);

        CategoryDto category = new CategoryDto();
        category.setId(1L);
        Long userId = jwt.getClaim("userId");

        ServiceOfferingResponseDto newServiceOffering = serviceOfferingService.createNewServiceOfferingByOwner(newServiceOfferingData, salon.getId(), category.getId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOffering);
    }


    @PutMapping("/update-service-offering/{serviceOfferingId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServiceOfferingResponseDto> updateServiceOffering(@RequestBody @Valid ServiceOfferingDto newServiceOfferingData, @PathVariable Long serviceOfferingId,@AuthenticationPrincipal Jwt jwt) throws  Exception {

        ServiceOfferingResponseDto newServiceOffering = serviceOfferingService.updateServiceOfferingByOwner(newServiceOfferingData, serviceOfferingId, jwt.getClaim("userId"));
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOffering);
    }

}
