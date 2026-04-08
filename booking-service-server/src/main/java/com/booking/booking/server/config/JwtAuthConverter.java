package com.booking.booking.server.config;

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
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        Object roleBase = source.getClaims().get("roles");
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
