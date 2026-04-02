package com.salon_micro_server.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class SalonDto {

    @NotBlank(message = "name is required")
    private String name;

    @ElementCollection
    private List<String> images;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "valid email is required.")
    private String email;

    @NotNull(message = "openTime is required")
    private LocalTime openTime;

    @NotNull(message = "closeTime is required")
    private LocalTime closeTime;

}
