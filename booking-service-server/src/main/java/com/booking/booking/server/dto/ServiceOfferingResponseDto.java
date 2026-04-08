package com.booking.booking.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceOfferingResponseDto {
    private Long id;
    private String serviceOfferingName;
    private String description;
    private int price;
    private int duration;
    private Long salonId;
    private Long categoryId;
    private Long ownerId;
    private String serviceOfferImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
