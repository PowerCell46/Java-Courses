package com.ItCareerElevatorSixthExercise.config;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.failureReserveItems.FailureReserveItemsDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.items-reserved-consumer.group-id}")
    private String itemsReservedConsumerGroup;

    @Value("${spring.kafka.failure-reserve-items-consumer.group-id}")
    private String failureReserveItemsConsumerGroup;

        @Bean
    public ConsumerFactory<String, FailureReserveItemsDTO> itemsReservedConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, itemsReservedConsumerGroup);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(FailureReserveItemsDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FailureReserveItemsDTO> itemsReservedKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, FailureReserveItemsDTO>();
        factory.setConsumerFactory(failureReserveItemsConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, FailureReserveItemsDTO> failureReserveItemsConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, failureReserveItemsConsumerGroup);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(FailureReserveItemsDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FailureReserveItemsDTO> failureReserveItemsKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, FailureReserveItemsDTO>();
        factory.setConsumerFactory(failureReserveItemsConsumerFactory());
        return factory;
    }
}
