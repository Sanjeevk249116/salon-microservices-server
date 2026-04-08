package com.service_offering.server.repository;


import com.service_offering.server.dto.ServiceOfferingResponseDto;
import com.service_offering.server.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    List<ServiceOffering> findBySalonId(Long salonId);
    Set<ServiceOffering> findByIdIn(Set<Long> ids);
}
