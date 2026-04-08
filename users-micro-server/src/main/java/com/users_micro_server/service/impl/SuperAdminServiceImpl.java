package com.users_micro_server.service.impl;


import com.users_micro_server.entity.Role;
import com.users_micro_server.entity.User;
import com.users_micro_server.enums.RoleEnum;
import com.users_micro_server.repository.RoleRepository;
import com.users_micro_server.repository.UserRepository;
import com.users_micro_server.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public String updateProfileRole(Long profileId, RoleEnum roleEnum) {

        User user = userRepository.findById(profileId).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        Role role = roleRepository.findByRoleName(roleEnum).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        user.getRole().add(role);
        userRepository.save(user);

        return "Updated Profile";
    }
}
