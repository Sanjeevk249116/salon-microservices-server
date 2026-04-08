package com.users_micro_server.dto;

import com.users_micro_server.enums.RoleEnum;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    private RoleEnum role;
}

