package com.gateway_micro.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/v1/auth/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {})
                        .authenticationEntryPoint((exchange, ex) -> {
                            ex.printStackTrace();
                            var response = exchange.getResponse();
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                            Map<String, Object> body = Map.of(
                                    "code", 401,
                                    "error", "Unauthorized",
                                    "message", "Invalid or missing token.",
                                    "path", exchange.getRequest().getPath().value()
                            );

                            try {
                                byte[] bytes = objectMapper.writeValueAsBytes(body);
                                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                                return response.writeWith(Mono.just(buffer));
                            } catch (Exception e) {
                                return Mono.error(e);
                            }
                        })
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((exchange, ex) -> {
                            var response = exchange.getResponse();
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                            Map<String, Object> body = Map.of(
                                    "code", 401,
                                    "error", "Unauthorized",
                                    "message", "Access Denied. You are not authorized to access this resource.",
                                    "path", exchange.getRequest().getPath().value()
                            );

                            try {
                                byte[] bytes = objectMapper.writeValueAsBytes(body);
                                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                                return response.writeWith(Mono.just(buffer));
                            } catch (Exception e) {
                                return Mono.error(e);
                            }
                        })
                )
                .build();
    }
}