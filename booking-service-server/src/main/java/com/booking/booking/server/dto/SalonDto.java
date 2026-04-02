package com.booking.booking.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class SalonDto {
    private Long id;
    private LocalTime openTime;
    private LocalTime closeTime;
}
