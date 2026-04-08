package com.service_offering.server.controller;

import com.service_offering.server.dto.*;
import com.service_offering.server.globalExceptionHandler.CustomException;
import com.service_offering.server.service.ServiceOfferingService;
import com.service_offering.server.service.client.CategoryClient;
import com.service_offering.server.service.client.GetSalonDetailClient;
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
@RequestMapping("/api/owner/service-offering")
public class ServiceOfferingSalonController {
    private final ServiceOfferingService serviceOfferingService;
    private final GetSalonDetailClient getSalonDetailClient;
    private final CategoryClient categoryClient;

    @PostMapping("/create-service-offering/{salonId}/{categoryId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServiceOfferingResponseDto> createServiceOffering(@RequestBody @Valid ServiceOfferingDto newServiceOfferingData, @PathVariable Long salonId, @PathVariable Long categoryId, @AuthenticationPrincipal Jwt jwt) throws Exception {

        Long userId = jwt.getClaim("userId");

        List<SalonResponseDto> salonResponseDto = getSalonDetailClient.getMySalonDetails("Bearer " + jwt.getTokenValue()).getBody();

        assert salonResponseDto != null;
        if (salonResponseDto.isEmpty()) {
            throw new CustomException("Salon not found");
        }

        boolean exists = salonResponseDto.stream()
                .anyMatch(salons -> salons.getId().equals(salonId));

        if (!exists) {
            throw new CustomException("You are not allowed to create a new category for this salon", 400);
        }

        CategoryResponseDto categoryResponseDto = categoryClient.readSingleCategory(categoryId).getBody();

        assert categoryResponseDto != null;
        if (!categoryResponseDto.getSalonId().equals(salonId)) {
            throw new CustomException("You are not allowed to create a new Service offer for this salon", 400);
        }


        ServiceOfferingResponseDto newServiceOffering = serviceOfferingService.createNewServiceOfferingByOwner(newServiceOfferingData, salonId, categoryId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOffering);
    }


    @PutMapping("/update-service-offering/{serviceOfferingId}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ServiceOfferingResponseDto> updateServiceOffering(@RequestBody @Valid ServiceOfferingDto newServiceOfferingData, @PathVariable Long serviceOfferingId, @AuthenticationPrincipal Jwt jwt) throws Exception {

        ServiceOfferingResponseDto newServiceOffering = serviceOfferingService.updateServiceOfferingByOwner(newServiceOfferingData, serviceOfferingId, jwt.getClaim("userId"));
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOffering);
    }

}
