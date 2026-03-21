package com.booking.booking.server.entity;

import com.booking.booking.server.domain.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class BookingService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long salonId;
    private Long customerId;
    private LocalDate startTime;
    private LocalDate endTime;
    private BookingStatus bookingStatus = BookingStatus.PENDING;
    private Set<Long> servicesIds;
    private int totalServices;

}
