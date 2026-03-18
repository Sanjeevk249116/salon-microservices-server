package com.salon.salon.server.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class SalonDto {

    @Column(nullable = false,unique = true)
    @NotBlank(message = "name is required")
    private String name;

    @Column(nullable = false)
    @ElementCollection
    private List<String> images;

    @Column(nullable = false)
    @NotBlank(message = "Address is required")
    private String address;

    @Column(nullable = false,unique = true)
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @Column(nullable = false)
    @NotBlank(message = "email is required")
    @Email(message = "valid email is required.")
    private String email;

    @NotNull(message = "ownerId is required")
    private Long ownerId;

    @NotNull(message = "openTime is required")
    private LocalTime openTime;

    @NotNull(message = "closeTime is required")
    private LocalTime closeTime;

}
