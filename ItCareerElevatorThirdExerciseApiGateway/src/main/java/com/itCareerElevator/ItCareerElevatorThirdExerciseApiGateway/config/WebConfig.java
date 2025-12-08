package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${persistence-microservice.endpoint}")
    private String PERSISTENCE_MICROSERVICE_BASE_URL;

    @Value("${http://127.0.0.1:8082}")
    private String FEED_MICROSERVICE_BASE_URL;

    @Bean
    public WebClient persistenceServiceWebClient() {
        return WebClient
                .builder()
                .baseUrl(PERSISTENCE_MICROSERVICE_BASE_URL)
                .build();
    }

    @Bean
    public WebClient feedServiceWebClient() {
        return WebClient
                .builder()
                .baseUrl(FEED_MICROSERVICE_BASE_URL)
                .build();
    }
}
