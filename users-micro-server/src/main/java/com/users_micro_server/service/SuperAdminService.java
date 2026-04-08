package com.users_micro_server.service;

import com.users_micro_server.enums.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public interface SuperAdminService {
    String updateProfileRole(Long profileId, RoleEnum role);
}
