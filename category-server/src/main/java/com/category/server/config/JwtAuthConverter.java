package com.category.server.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        Object claimRole=source.getClaims().get("role");
        Collection<SimpleGrantedAuthority> authorities;
        if(claimRole instanceof List<?> roleList){
            authorities=roleList.stream().map((role)->new SimpleGrantedAuthority(role.toString())).toList();
        } else if (claimRole instanceof Set<?> roleSet) {
            authorities=roleSet.stream().map((role)->new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());
        }else{
            authorities=List.of();
        }
        return new JwtAuthenticationToken(source,authorities,source.getSubject());
    }
}
