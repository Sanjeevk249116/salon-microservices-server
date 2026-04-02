package com.service_offering.server.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceOfferingDto {
    @NotBlank(message = "Service Offering name is required")
    private String serviceOfferingName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 1,message = "Price must be greater than 1")
    private int price;

    @NotNull(message = "Duration is required")
    private int duration;

    @NotBlank(message = "Service Offering Image is required")
    private String serviceOfferImage;
}
