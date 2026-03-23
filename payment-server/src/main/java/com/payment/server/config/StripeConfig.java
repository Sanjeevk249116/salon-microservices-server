package com.payment.server.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.api.key.value}")
    private String stripeApiKey;


    @Bean
    public String stripeApiKey() {
        Stripe.apiKey = stripeApiKey;
        return stripeApiKey;
    }
}
