package com.salon.salon.server.repository;

import com.salon.salon.server.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalonRepository extends JpaRepository<Salon, Long> {
    Optional<Salon> findByNameIgnoreCase(String name);

    @Query(
            "SELECT s FROM Salon s WHERE " +
                    "LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                    "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                    "LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Salon> searchSalons(@Param("keyword") String keyword);
}
