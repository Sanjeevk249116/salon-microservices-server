package com.auth.server.repository;


import com.auth.server.entity.RefreshToken;
import com.auth.server.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByUser(UserAuth user);
    Optional<RefreshToken> findByToken(String token);
}
