package com.auth.server.service.impl;


import com.auth.server.dto.clientDto.UserRequestDto;
import com.auth.server.dto.request.LoginRequest;
import com.auth.server.dto.request.RefreshTokenRequest;
import com.auth.server.dto.request.RegisterRequest;
import com.auth.server.dto.response.AuthResponse;
import com.auth.server.entity.RefreshToken;
import com.auth.server.entity.Role;
import com.auth.server.entity.UserAuth;
import com.auth.server.enums.RoleEnum;
import com.auth.server.exceptionHandling.CustomException;
import com.auth.server.repository.RoleRepository;
import com.auth.server.repository.UserAuthRepository;
import com.auth.server.security.CustomerUserDetail;
import com.auth.server.service.AuthService;
import com.auth.server.service.client.CreateUserProfileClient;
import com.auth.server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAuthRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenImpl refreshTokenImpl;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final CreateUserProfileClient createUserProfileClient;

    @Override
    @Transactional
    public AuthResponse registerNewUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new CustomException("Email already exists with" + registerRequest.getEmail(), 400);
        }

        Role role = roleRepository.findByRoleName(RoleEnum.ROLE_USER).orElseThrow(() -> new IllegalArgumentException("role not found"));
        UserAuth newUser = modelMapper.map(registerRequest, UserAuth.class);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(Set.of(role));

        UserAuth savedUser = userRepository.save(newUser);

        CustomerUserDetail customerUserDetail = new CustomerUserDetail(savedUser);
        String accessToken = jwtUtils.generateAccessToken(customerUserDetail, savedUser.getId());

        RefreshToken refreshToken = refreshTokenImpl.createNewRefreshToken(savedUser);

        //create user profile
        String bearerToken = "Bearer " + accessToken;
        String fullName = savedUser.getFirstName() + " " + savedUser.getLastName();

        UserRequestDto newUserRequestDto = new UserRequestDto();

        newUserRequestDto.setEmail(savedUser.getEmail());
        newUserRequestDto.setPhone(savedUser.getPhoneNumber());
        newUserRequestDto.setUserName(registerRequest.getUserName());
        newUserRequestDto.setFullName(fullName);

        createUserProfileClient.createUserProfile(newUserRequestDto, bearerToken);

        return buildApiResponse(accessToken, refreshToken.getToken(), customerUserDetail);
    }

    @Override
    @Transactional
    public AuthResponse loginUser(LoginRequest loginRequest) {
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            CustomerUserDetail customerUserDetail = (CustomerUserDetail) authentication.getPrincipal();

            UserAuth user = customerUserDetail.getUserAuth();
            String accessToken = jwtUtils.generateAccessToken(customerUserDetail, user.getId());
            RefreshToken refreshToken = refreshTokenImpl.createNewRefreshToken(user);

            return buildApiResponse(accessToken, refreshToken.getToken(), customerUserDetail);
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid email or password", 401);
        } catch (UsernameNotFoundException e) {
            throw new CustomException("User not found", 400);
        }
    }

    @Override
    @Transactional
    public AuthResponse generateToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenImpl.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

        UserAuth user = refreshToken.getUser();
        CustomerUserDetail customerUserDetail = new CustomerUserDetail(user);
        String accessToken = jwtUtils.generateAccessToken(customerUserDetail, user.getId());
        RefreshToken refreshToken1 = refreshTokenImpl.createNewRefreshToken(user);
        return buildApiResponse(accessToken, refreshToken1.getToken(), customerUserDetail);

    }

    @Override
    @Transactional
    public String logoutUser(String email) {
        UserAuth user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found with email" + email));
        refreshTokenImpl.deleteByUser(user);
        return "Account has been logged out";
    }

    private AuthResponse buildApiResponse(String accessToken, String token, CustomerUserDetail customerUserDetail) {
        List<String> roles = customerUserDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .email(customerUserDetail.getUsername())
                .roles(roles)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getAccess_token_expiration())
                .build();
    }
}
