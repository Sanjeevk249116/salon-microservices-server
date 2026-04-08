package com.category.server.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponseClientDto {
    private long id;
    private String fullName;
    private String email;
    private String phone;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<String> role;
}
