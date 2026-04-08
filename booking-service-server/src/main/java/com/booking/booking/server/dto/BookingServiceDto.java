package com.booking.booking.server.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingServiceDto {
    private LocalDateTime startTime;
    private Set<Long> servicesIds;
}
