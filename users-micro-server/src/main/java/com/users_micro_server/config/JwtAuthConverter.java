package com.users_micro_server.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Object rolesClaim = jwt.getClaims().get("roles");
        Collection<SimpleGrantedAuthority> authorities;

        if (rolesClaim instanceof List<?> rolesList) {
            authorities = rolesList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .collect(Collectors.toList());
        } else if (rolesClaim instanceof Set<?> rolesSet) {
            authorities = rolesSet.stream()
                    .map(role -> new SimpleGrantedAuthority( role.toString()))
                    .collect(Collectors.toList());
        } else {
            authorities = List.of();
        }

        return new JwtAuthenticationToken(jwt, authorities, jwt.getSubject());
    }
}