package com.auth.server.service.impl;


import com.auth.server.entity.RefreshToken;
import com.auth.server.entity.UserAuth;
import com.auth.server.repository.RefreshTokenRepository;
import com.auth.server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenImpl {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Transactional
    public RefreshToken createNewRefreshToken(UserAuth user) {
        refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken = new RefreshToken();

        String token = jwtUtils.generateRefreshToken(user, refreshTokenExpiration);
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));

        return refreshTokenRepository.save(refreshToken);
    }


    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken1 = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Refresh token not found"));
        if (refreshToken1.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh Token has expired. Please login again.");
        }
        return refreshToken1;
    }

    @Transactional
    public void deleteByUser(UserAuth user) {
        refreshTokenRepository.deleteByUser(user);
    }


}
