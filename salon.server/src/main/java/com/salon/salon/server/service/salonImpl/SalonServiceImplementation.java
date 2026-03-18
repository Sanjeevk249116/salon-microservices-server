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
}
