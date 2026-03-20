package com.service_offering.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceOfferingDto {
    @NotBlank(message = "Service Offering name is required")
    private String serviceOfferingName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Price is required")
    private int price;

    @NotBlank(message = "Duration is required")
    private int duration;

    @NotBlank(message = "Service Offering Image is required")
    private String serviceOfferImage;
}
