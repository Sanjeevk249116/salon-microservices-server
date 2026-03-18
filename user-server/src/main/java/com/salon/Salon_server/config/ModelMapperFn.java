package com.salon.Salon_server.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperFn {

    @Bean
    public ModelMapper modelMapperFun() {
        return new ModelMapper();
    }

}
