package com.payment.server.config;

import com.payment.server.dto.UserResponseClientDto;
import com.payment.server.service.client.GetUserDetailsClient;
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

        String tokenValue = source.getTokenValue();

        UserResponseClientDto userResponseClientDto = getUserDetailsClient.getUserProfile("Bearer " + tokenValue).getBody();


        assert userResponseClientDto != null;
        Object claimRole = userResponseClientDto.getRole();
        Collection<SimpleGrantedAuthority> authorities;
        if (claimRole instanceof List<?> roleList) {
            authorities = roleList.stream().map((role) -> new SimpleGrantedAuthority(role.toString())).toList();
        } else if (claimRole instanceof Set<?> roleSet) {
            authorities = roleSet.stream().map((role) -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());
        } else {
            authorities = List.of();
        }
        return new JwtAuthenticationToken(source, authorities, source.getSubject());
    }
}
