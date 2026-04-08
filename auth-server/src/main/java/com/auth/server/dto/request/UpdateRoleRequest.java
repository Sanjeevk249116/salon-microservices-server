package com.auth.server.dto.request;


import com.auth.server.enums.RoleEnum;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    private RoleEnum role;
}

