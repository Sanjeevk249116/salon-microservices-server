package com.service_offering.server.controller;

import com.service_offering.server.dto.ServiceOfferingResponseDto;
import com.service_offering.server.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<ServiceOfferingResponseDto>> readAllServiceOfferBySalon(@PathVariable Long salonId) {
        List<ServiceOfferingResponseDto> readAllServiceBySalon = serviceOfferingService.readAllServiceOfferBySalon(salonId);
        return ResponseEntity.ok(readAllServiceBySalon);
    }

    @GetMapping("/read/all-service-by-ids")
    public ResponseEntity<Set<ServiceOfferingResponseDto>> readAllServiceOfferByIds(@RequestBody Set<Long> ids) {
        Set<ServiceOfferingResponseDto> serviceOfferingResponseDtos=serviceOfferingService.readAllServiceOfferByIds(ids);
        return ResponseEntity.ok(serviceOfferingResponseDtos);
    }

}
