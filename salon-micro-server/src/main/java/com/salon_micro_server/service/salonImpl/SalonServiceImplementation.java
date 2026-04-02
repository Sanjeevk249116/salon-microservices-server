package com.salon_micro_server.service.salonImpl;


import com.salon_micro_server.dto.SalonDto;
import com.salon_micro_server.entity.Salon;
import com.salon_micro_server.globalExceptionHandler.CustomException;
import com.salon_micro_server.repository.SalonRepository;
import com.salon_micro_server.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalonServiceImplementation implements SalonService {

    private final SalonRepository salonRepository;
    private final ModelMapper modelMapper;

    @Override
    public Salon createNewSalon(SalonDto newSalonData, Long userId) {
        Optional<Salon> existingSalonWithName = salonRepository.findByNameIgnoreCase(newSalonData.getName());
        if (existingSalonWithName.isPresent()) {
            throw new CustomException("Salon name is already added.");
        }

        Salon newSalon = modelMapper.map(newSalonData, Salon.class);
        newSalon.setId(null);
        newSalon.setOwnerId(userId);
        return salonRepository.save(newSalon);
    }

    @Override
    public Salon updateSalon(SalonDto updateSalonData, Long salonId, Long ownerId) {

        Salon existingSalon = salonRepository.findById(salonId)
                .orElseThrow(() -> new CustomException("Salon not found with id: " + salonId));

        if (!existingSalon.getOwnerId().equals(ownerId)) {
            throw new CustomException("You are not allow to perform this action");
        }

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

        if (updateSalonData.getOpenTime() != null) {
            existingSalon.setOpenTime(updateSalonData.getOpenTime());
        }

        if (updateSalonData.getCloseTime() != null) {
            existingSalon.setCloseTime(updateSalonData.getCloseTime());
        }

        return salonRepository.save(existingSalon);
    }

    @Override
    public List<Salon> getAllSalonList() {
        System.out.println("hello request is service comming here >>>>>>>>>>>>>************");

        System.out.println("getAllSalonList "+salonRepository.findAll());
        return salonRepository.findAll();
    }

    @Override
    public Salon readSingleSalonById(Long salonId) {
        return salonRepository.findById(salonId).orElseThrow(() -> new CustomException("Salon not found"));

    }

    @Override
    public String deleteSalonAccount(Long salonId, Long id) {
        Salon salon = salonRepository.findById(salonId).orElseThrow(() -> new CustomException("Salon not found to delete"));
        if (!salon.getOwnerId().equals(id)) {
            throw new CustomException("You are not allow to delete");
        }
        return "Account delete successfully.";
    }

    @Override
    public List<Salon> searchSalon(String keyword) {
        return salonRepository.searchSalons(keyword);
    }
}
