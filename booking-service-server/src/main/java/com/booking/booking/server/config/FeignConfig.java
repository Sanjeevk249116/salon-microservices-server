package com.booking.booking.server.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public CustomErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
