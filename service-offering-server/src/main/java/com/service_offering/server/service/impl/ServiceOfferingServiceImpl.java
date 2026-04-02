package com.service_offering.server.service.impl;

import com.service_offering.server.dto.ServiceOfferingDto;
import com.service_offering.server.dto.ServiceOfferingResponseDto;
import com.service_offering.server.entity.ServiceOffering;
import com.service_offering.server.globalExceptionHandler.CustomException;
import com.service_offering.server.repository.ServiceOfferingRepository;
import com.service_offering.server.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ModelMapper modelMapper;

    @Override
    public ServiceOfferingResponseDto createNewServiceOfferingByOwner(ServiceOfferingDto newServiceOfferingData,
                                                                      Long salonID,
                                                                      Long categoryId,
                                                                      Long userId) {
        ServiceOffering serviceOfferingData = modelMapper.map(newServiceOfferingData, ServiceOffering.class);
        serviceOfferingData.setOwnerId(userId);
        serviceOfferingData.setCategoryId(categoryId);
        serviceOfferingData.setSalonId(salonID);

        ServiceOffering newCreatedServiceOffering = serviceOfferingRepository.save(serviceOfferingData);

        return modelMapper.map(newCreatedServiceOffering, ServiceOfferingResponseDto.class);
    }

    @Override
    public ServiceOfferingResponseDto updateServiceOfferingByOwner(ServiceOfferingDto newServiceOfferingData, Long serviceOfferingId, Long ownerId) {

        ServiceOffering existingServiceOffer = serviceOfferingRepository.findById(serviceOfferingId).orElseThrow(() -> new CustomException("Service Offer is not found"));
        if (!existingServiceOffer.getOwnerId().equals(ownerId)) {
            throw new CustomException("You are not allow to perform this action");
        }

        if (newServiceOfferingData.getServiceOfferingName() != null) {
            existingServiceOffer.setServiceOfferingName(newServiceOfferingData.getServiceOfferingName());
        }
        if (newServiceOfferingData.getServiceOfferImage() != null) {
            existingServiceOffer.setServiceOfferImage(newServiceOfferingData.getServiceOfferImage());
        }
        if (newServiceOfferingData.getDescription() != null) {
            existingServiceOffer.setDescription(newServiceOfferingData.getDescription());
        }
        if (newServiceOfferingData.getPrice() != 0) {
            existingServiceOffer.setPrice(newServiceOfferingData.getPrice());
        }
        if (newServiceOfferingData.getDuration() != 0) {
            existingServiceOffer.setDuration(newServiceOfferingData.getDuration());
        }

        ServiceOffering updatedServiceOffer = serviceOfferingRepository.save(existingServiceOffer);

        return modelMapper.map(updatedServiceOffer, ServiceOfferingResponseDto.class);
    }

    @Override
    public Set<ServiceOfferingResponseDto> getAllServiceOfferingList() {
        return serviceOfferingRepository.findAll().stream().map(item->modelMapper.map(item,ServiceOfferingResponseDto.class)).collect(Collectors.toSet());
    }

    @Override
    public ServiceOfferingResponseDto getSingleServiceOffer(Long serviceOfferingId) {
        ServiceOffering readSingleServiceOffer=serviceOfferingRepository.findById(serviceOfferingId).orElseThrow(()->new IllegalArgumentException("Service offer is not found"));
        return modelMapper.map(readSingleServiceOffer,ServiceOfferingResponseDto.class);
    }

    @Override
    public List<ServiceOfferingResponseDto> readAllServiceOfferBySalon(Long salonId) {
        return serviceOfferingRepository.findBySalonId(salonId).stream().map(item->modelMapper.map(item,ServiceOfferingResponseDto.class)).toList();
    }
}
