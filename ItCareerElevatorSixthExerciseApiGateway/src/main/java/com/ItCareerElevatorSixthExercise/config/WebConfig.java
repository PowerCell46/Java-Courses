package com.ItCareerElevatorSixthExercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${orders-microservice.base-endpoint}")
    private String ORDERS_MICROSERVICE_BASE_URL;

    @Bean
    public WebClient ordersWebClient() {
        return WebClient
                .builder()
                .baseUrl(ORDERS_MICROSERVICE_BASE_URL)
                .build();
    }
}
