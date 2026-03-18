package com.salon.salon.server.service.salonImpl;

import com.salon.salon.server.dto.SalonDto;
import com.salon.salon.server.entity.Salon;
import com.salon.salon.server.globalExceptionHandler.CustomException;
import com.salon.salon.server.repository.SalonRepository;
import com.salon.salon.server.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalonServiceImplementation implements SalonService {

    private final SalonRepository salonRepository;
    private final ModelMapper modelMapper;

    @Override
    public Salon createNewSalon(SalonDto newSalonData) {
        Optional<Salon> existingSalonWithName = salonRepository.findByNameIgnoreCase(newSalonData.getName());
        if (existingSalonWithName.isPresent()) {
            throw new CustomException("Salon name is already added.");
        }
        Salon newSalon = modelMapper.map(newSalonData, Salon.class);
        newSalon.setId(null);
        return salonRepository.save(newSalon);
    }

    @Override
    public Salon updateSalon(SalonDto updateSalonData, Long salonId) {

        Salon existingSalon = salonRepository.findById(salonId)
                .orElseThrow(() -> new CustomException("Salon not found with id: " + salonId));

        if (updateSalonData.getName() != null) {
            existingSalon.setName(updateSalonData.getName());
        }

        if (updateSalonData.getImages() != null) {
            existingSalon.setImages(updateSalonData.getImages());
        }

        if (updateSalonData.getAddress() != null) {
            existingSalon.setAddress(updateSalonData.getAddress());
        }

        if (updateSalonData.getPhoneNumber() != null) {
            existingSalon.setPhoneNumber(updateSalonData.getPhoneNumber());
        }

        if (updateSalonData.getEmail() != null) {
            existingSalon.setEmail(updateSalonData.getEmail());
        }

        if (updateSalonData.getOwnerId() != null) {
            existingSalon.setOwnerId(updateSalonData.getOwnerId());
        }

        if (updateSalonData.getOpenTime() != null) {
            existingSalon.setOpenTime(updateSalonData.getOpenTime());
        }

        if (updateSalonData.getCloseTime() != null) {
            existingSalon.setCloseTime(updateSalonData.getCloseTime());
        }

        return salonRepository.save(existingSalon);
    }
}
