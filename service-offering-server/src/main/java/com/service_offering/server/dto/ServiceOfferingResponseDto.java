package com.service_offering.server.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String serviceOfferImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
