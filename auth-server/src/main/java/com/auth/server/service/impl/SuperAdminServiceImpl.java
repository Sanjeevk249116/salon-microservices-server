package com.auth.server.service.impl;

import com.auth.server.entity.Role;
import com.auth.server.entity.UserAuth;
import com.auth.server.enums.RoleEnum;
import com.auth.server.repository.RoleRepository;
import com.auth.server.repository.UserAuthRepository;

import com.auth.server.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserAuthRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public String updateProfileRole(Long profileId, RoleEnum roleEnum) {

        UserAuth user = userRepository.findById(profileId).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        Role role = roleRepository.findByRoleName(roleEnum).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        user.getRole().add(role);
        userRepository.save(user);

        return "Updated Profile";
    }
}
