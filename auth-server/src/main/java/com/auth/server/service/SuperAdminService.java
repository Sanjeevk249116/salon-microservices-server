package com.auth.server.service;


import com.auth.server.enums.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public interface SuperAdminService {
    String updateProfileRole(Long profileId, RoleEnum role);
}
