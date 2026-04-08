package com.booking.booking.server.config;

import com.booking.booking.server.dto.UserResponseClientDto;
import com.booking.booking.server.service.client.GetUserDetailsClient;
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

    private final GetUserDetailsClient getUserDetailsClient;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        UserResponseClientDto user=getUserDetailsClient.getUserProfile(source.getTokenValue()).getBody();

        assert user != null;
        Object roleBase = user.getRole();
        Collection<SimpleGrantedAuthority> authorities;

        if (roleBase instanceof List<?> rolesList) {
            authorities = rolesList.stream().map((role) -> new SimpleGrantedAuthority(role.toString())).toList();
        } else if (roleBase instanceof Set<?> rolesSet) {
            authorities = rolesSet.stream().map((role) -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());
        } else {
            authorities = List.of();
        }

        return new JwtAuthenticationToken(source, authorities, source.getSubject());
    }

}
