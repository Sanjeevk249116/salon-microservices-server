package com.service_offering.server.service;

import com.service_offering.server.dto.ServiceOfferingDto;
import com.service_offering.server.dto.ServiceOfferingResponseDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ServiceOfferingService {
    ServiceOfferingResponseDto createNewServiceOfferingByOwner(ServiceOfferingDto newServiceOfferingData, Long salonID, Long categoryId, Long userId);

    ServiceOfferingResponseDto updateServiceOfferingByOwner(@Valid ServiceOfferingDto newServiceOfferingData, Long serviceOfferingId, Long ownerId);

    Set<ServiceOfferingResponseDto> getAllServiceOfferingList();

    ServiceOfferingResponseDto getSingleServiceOffer(Long serviceOfferingId);

    List<ServiceOfferingResponseDto> readAllServiceOfferBySalon(Long salonId);

    Set<ServiceOfferingResponseDto> readAllServiceOfferByIds(Set<Long> ids);
}
