package com.users_micro_server.repository;


import com.users_micro_server.entity.Role;import com.users_micro_server.enums.RoleEnum;import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleEnum roleName);
}
