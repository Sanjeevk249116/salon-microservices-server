package com.booking.booking.server.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class SalonResponseDto {
    private Long id;

    private String name;
    private List<String> images;
    private String address;
    private String city;
    private String phoneNumber;
    private String email;
    private Long ownerId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
