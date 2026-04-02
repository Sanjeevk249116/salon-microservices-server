package com.booking.booking.server.repository;

import com.booking.booking.server.entity.BookingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceBookingRepository extends JpaRepository<BookingService, Long> {
    List<BookingService> findByCustomerId(Long customerId);

    List<BookingService> findBySalonId(Long salonId);
}
