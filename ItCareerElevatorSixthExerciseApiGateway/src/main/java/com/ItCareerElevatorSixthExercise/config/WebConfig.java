package com.ItCareerElevatorSixthExercise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${users-microservice.base-endpoint}")
    private String USERS_MICROSERVICE_BASE_URL;

    @Value("${orders-microservice.base-endpoint}")
    private String ORDERS_MICROSERVICE_BASE_URL;

    @Value("${products-microservice.base-endpoint}")
    private String PRODUCTS_MICROSERVICE_BASE_URL;

    @Bean
    public WebClient userServiceWebClient() {
        return WebClient
                .builder()
                .baseUrl(USERS_MICROSERVICE_BASE_URL)
                .build();
    }

    @Bean
    public WebClient orderServiceWebClient() {
        return WebClient
                .builder()
                .baseUrl(ORDERS_MICROSERVICE_BASE_URL)
                .build();
    }

    @Bean
    public WebClient productServiceWebClient() {
        return WebClient
                .builder()
                .baseUrl(PRODUCTS_MICROSERVICE_BASE_URL)
                .build();
    }
}
