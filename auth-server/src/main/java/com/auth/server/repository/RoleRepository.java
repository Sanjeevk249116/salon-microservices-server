package com.auth.server.repository;


import com.auth.server.entity.Role;
import com.auth.server.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleEnum roleName);
}
