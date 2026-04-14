package com.users_micro_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users_micro_server.exceptionHandling.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers("/api/user/create").permitAll()
                        .requestMatchers("/api/user/admin/**").hasAnyRole("SUPERADMIN", "ADMIN")
                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN","SUPERADMIN")
                        .requestMatchers("/api/v1/owner/**").hasAnyRole("OWNER", "ADMIN", "SUPERADMIN")
                        .requestMatchers("/api/v2/admin/**").hasRole("SUPERADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                        .authenticationEntryPoint((request, response, authException) -> {

                            response.setContentType("application/json;charset=UTF-8");
                            Map<String, Object> map = new HashMap<>();

                            String errorMessage = authException.getMessage();
                            String errorCode = "UNAUTHORIZED";
                            int statusCode = 401;

                            if (authException instanceof UserNotFoundException unf) {
                                statusCode = unf.getCode();
                                errorCode = "USER_NOT_FOUND";
                                errorMessage = unf.getMessage();

                            } else if (authException instanceof org.springframework.security.oauth2.core.OAuth2AuthenticationException oauthEx) {
                                String error = oauthEx.getError().getErrorCode();

                                if ("user_not_found".equals(error)) {
                                    errorCode = "USER_NOT_FOUND";
                                    errorMessage = oauthEx.getError().getDescription();
                                } else if (errorMessage != null && errorMessage.toLowerCase().contains("expired")) {
                                    errorCode = "TOKEN_EXPIRED";
                                    errorMessage = "Token has expired. Please refresh your token or login again.";
                                } else {
                                    errorCode = "INVALID_TOKEN";
                                }

                            } else if (errorMessage != null && errorMessage.toLowerCase().contains("expired")) {
                                errorCode = "TOKEN_EXPIRED";
                                errorMessage = "Token has expired. Please refresh your token or login again.";
                            }

                            response.setStatus(statusCode);
                            map.put("statusCode", statusCode);
                            map.put("error", errorCode);
                            map.put("message", errorMessage);
                            map.put("timestamp", System.currentTimeMillis());
                            map.put("path", request.getRequestURI());

                            objectMapper.writeValue(response.getWriter(), map);
                        })
                )
                .exceptionHandling(exceptionError -> exceptionError
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json;charset=UTF-8");
                            Map<String, Object> map = new HashMap<>();
                            map.put("statusCode", 401);
                            map.put("error", "UNAUTHORIZED");
                            map.put("message", "Authentication required. Please provide a valid token.");
                            map.put("timestamp", System.currentTimeMillis());
                            map.put("path", request.getRequestURI());
                            objectMapper.writeValue(response.getWriter(), map);
                        })
                        .accessDeniedHandler((request, response, authException) -> {
                            response.setStatus(403);
                            response.setContentType("application/json;charset=UTF-8");
                            Map<String, Object> map = new HashMap<>();
                            map.put("statusCode", 403);
                            map.put("error", "ACCESS_DENIED");
                            map.put("message", "Access Denied. You are not authorized to access this resource.");
                            map.put("timestamp", System.currentTimeMillis());
                            map.put("path", request.getRequestURI());
                            objectMapper.writeValue(response.getWriter(), map);
                        })
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:4200",
                "https://yourdomain.com"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}