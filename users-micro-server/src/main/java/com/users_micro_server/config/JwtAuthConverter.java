package com.users_micro_server.config;

import com.users_micro_server.entity.User;
import com.users_micro_server.exceptionHandling.CustomException;
import com.users_micro_server.exceptionHandling.UserNotFoundException;
import com.users_micro_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        String username = jwt.getSubject();
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException("User not found. So please login with correct username", 401);
        }

        Object rolesClaim = jwt.getClaims().get("roles");
        Collection<SimpleGrantedAuthority> authorities;

        if (rolesClaim instanceof List<?> rolesList) {
            authorities = rolesList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .collect(Collectors.toList());
        } else if (rolesClaim instanceof Set<?> rolesSet) {
            authorities = rolesSet.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .collect(Collectors.toList());
        } else {
            authorities = List.of();
        }


        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
}