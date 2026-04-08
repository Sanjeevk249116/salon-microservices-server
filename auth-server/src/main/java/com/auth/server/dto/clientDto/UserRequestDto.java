package com.auth.server.dto.clientDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    private String fullName;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    private String phone;
}
