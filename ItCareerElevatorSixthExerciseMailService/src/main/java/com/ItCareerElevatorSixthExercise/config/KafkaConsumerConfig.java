package com.ItCareerElevatorSixthExercise.config;

import com.ItCareerElevatorSixthExercise.DTOs.orderCompleted.OrderCompletedDTO;
import com.ItCareerElevatorSixthExercise.DTOs.userRegistered.UserRegisteredDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.user-registered.group-id}")
    private String userRegisteredConsumerGroupId;

    @Value("${spring.kafka.order-completed.group-id}")
    private String orderCompletedConsumerGroupId;

    @Bean
    public ConsumerFactory<String, UserRegisteredDTO> userRegisteredConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, userRegisteredConsumerGroupId);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(UserRegisteredDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredDTO> userRegisteredKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserRegisteredDTO>();
        factory.setConsumerFactory(userRegisteredConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OrderCompletedDTO> orderCompletedConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, orderCompletedConsumerGroupId);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(OrderCompletedDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCompletedDTO> orderCompletedKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderCompletedDTO>();
        factory.setConsumerFactory(orderCompletedConsumerFactory());
        return factory;
    }
}
