package com.auth.server.security;

import com.auth.server.entity.UserAuth;
import com.auth.server.repository.UserAuthRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("user not found with email: " + username));
        return new CustomerUserDetail(userAuth);
    }
}
