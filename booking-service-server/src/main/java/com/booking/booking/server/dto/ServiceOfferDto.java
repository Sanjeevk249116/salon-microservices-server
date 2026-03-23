package com.booking.booking.server.dto;

import lombok.Data;

@Data
public class ServiceOfferDto {
    private Long id;
    private String serviceOfferingName;
    private String description;
    private int price;
    private int duration;
    private String serviceOfferImage;
}
