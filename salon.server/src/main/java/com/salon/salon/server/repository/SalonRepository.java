package com.salon.salon.server.repository;

import com.salon.salon.server.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalonRepository extends JpaRepository<Salon,Long> {
    Optional<Salon> findByNameIgnoreCase(String name);
}
