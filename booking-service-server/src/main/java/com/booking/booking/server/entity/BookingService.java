package com.booking.booking.server.entity;

import com.booking.booking.server.domain.BookingStatus;
import jakarta.persistence.*;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    @ElementCollection
    private Set<Long> servicesIds;
    private int totalPrice;

}
