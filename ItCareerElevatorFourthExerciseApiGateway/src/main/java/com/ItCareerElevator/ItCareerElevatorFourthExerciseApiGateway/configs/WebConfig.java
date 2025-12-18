package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${management-microservice.base-endpoint}")
    private String MANAGEMENT_MICROSERVICE_BASE_URL;

    @Value("${orders-microservice.base-endpoint}")
    private String ORDERS_MICROSERVICE_BASE_URL;

    @Bean
    public WebClient managementWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024)) // 10 MB
                .build();

        return WebClient
                .builder()
                .baseUrl(MANAGEMENT_MICROSERVICE_BASE_URL)
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public WebClient ordersWebClient() {
        return WebClient
                .builder()
                .baseUrl(ORDERS_MICROSERVICE_BASE_URL)
                .build();
    }
}
