package com.ItCareerElevatorSixthExercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${payment-microservice.base-endpoint}")
    private String PAYMENT_MICROSERVICE_BASE_URL;

    @Bean
    public WebClient paymentServiceWebClient() {
        return WebClient
                .builder()
                .baseUrl(PAYMENT_MICROSERVICE_BASE_URL)
                .build();
    }
}
