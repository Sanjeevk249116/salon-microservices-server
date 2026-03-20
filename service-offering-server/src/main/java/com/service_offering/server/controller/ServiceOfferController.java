package com.service_offering.server.controller;

import com.service_offering.server.dto.ServiceOfferingResponseDto;
import com.service_offering.server.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offer")
public class ServiceOfferController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/read/all-list")
    public ResponseEntity<Set<ServiceOfferingResponseDto>> getAllServiceOfferingList() {
        Set<ServiceOfferingResponseDto> getAllServiceList = serviceOfferingService.getAllServiceOfferingList();
        return ResponseEntity.ok(getAllServiceList);
    }

    @GetMapping("/read/single-service-offering/{serviceOfferingId}")
    public ResponseEntity<ServiceOfferingResponseDto> readSingleServiceOffer(@PathVariable Long serviceOfferingId) {
        ServiceOfferingResponseDto singleServiceOffer = serviceOfferingService.getSingleServiceOffer(serviceOfferingId);
        return ResponseEntity.ok(singleServiceOffer);
    }

    @GetMapping("/read/all-service-offer-by-salonId/{salonId}")
    public ResponseEntity<Set<ServiceOfferingResponseDto>> readAllServiceOfferBySalon(@PathVariable Long salonId) {
        Set<ServiceOfferingResponseDto> readAllServiceBySalon = serviceOfferingService.readAllServiceOfferBySalon(salonId);
        return ResponseEntity.ok(readAllServiceBySalon);
    }


}
