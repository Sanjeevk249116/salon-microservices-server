package com.users_micro_server.dto;

import com.users_micro_server.enums.RoleEnum;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponseDto {
    private long id;
    private String fullName;
    private String email;
    private String phone;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RoleEnum> role;
}
