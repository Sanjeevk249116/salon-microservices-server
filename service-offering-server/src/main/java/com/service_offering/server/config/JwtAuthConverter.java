package com.service_offering.server.config;

import com.service_offering.server.dto.UserResponseClientDto;
import com.service_offering.server.service.client.GetUserDetailsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final GetUserDetailsClient getUserDetailsClient;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        UserResponseClientDto userResponseClientDto=getUserDetailsClient.getUserProfile("Bearer "+source.getTokenValue()).getBody();


        assert userResponseClientDto != null;
        Object jwtRole=userResponseClientDto.getRole();
        Collection<SimpleGrantedAuthority> grantedAuthorities ;

        if(jwtRole instanceof List<?> jwtRoleList){
            grantedAuthorities = jwtRoleList.stream().map((role)->new SimpleGrantedAuthority(role.toString())).toList();
        }else if(jwtRole instanceof Set<?> jwtRoleSet){
            grantedAuthorities=jwtRoleSet.stream().map((role)->new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());
        }else{
            grantedAuthorities=List.of();
        }

        return new JwtAuthenticationToken(source,grantedAuthorities,source.getSubject());
    }

}
