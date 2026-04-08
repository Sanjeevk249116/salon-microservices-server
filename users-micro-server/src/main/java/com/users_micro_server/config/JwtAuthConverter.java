package com.users_micro_server.config;

import com.users_micro_server.entity.Role;
import com.users_micro_server.entity.User;
import com.users_micro_server.enums.RoleEnum;
import com.users_micro_server.exceptionHandling.UserNotFoundException;
import com.users_micro_server.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes())
                        .getRequest();

        String path = request.getRequestURI();
        Collection<SimpleGrantedAuthority> authorities = List.of();
        if (!path.contains("/api/user/create")) {

            User user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UserNotFoundException(
                        "User not found. Please login with correct username", 401
                );
            }

            Set<RoleEnum> roleEnumSet = user.getRole()
                    .stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toSet());

            authorities = roleEnumSet.stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .collect(Collectors.toList());
        }

        return new JwtAuthenticationToken(jwt, authorities, username);
    }
}