package com.ItCareerElevatorSixthExercise.config;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.FailureReserveItemsDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.PaymentUnsuccessfulDTO;
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

    @Value("${spring.kafka.payment-successful.group-id}")
    private String successfulPaymentConsumerGroup;

    @Value("${spring.kafka.payment-unsuccessful.group-id}")
    private String unsuccessfulPaymentConsumerGroup;

    @Bean
    public ConsumerFactory<String, ReservedOrderDTO> itemsReservedConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, itemsReservedConsumerGroup);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(ReservedOrderDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReservedOrderDTO> itemsReservedKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ReservedOrderDTO>();
        factory.setConsumerFactory(itemsReservedConsumerFactory());
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

    @Bean
    public ConsumerFactory<String, PaymentSuccessfulDTO> paymentSuccessfulConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, successfulPaymentConsumerGroup);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(PaymentSuccessfulDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessfulDTO> paymentSuccessfulKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentSuccessfulDTO>();
        factory.setConsumerFactory(paymentSuccessfulConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentUnsuccessfulDTO> paymentUnsuccessfulConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, unsuccessfulPaymentConsumerGroup);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(PaymentUnsuccessfulDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentUnsuccessfulDTO> paymentUnsuccessfulKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentUnsuccessfulDTO>();
        factory.setConsumerFactory(paymentUnsuccessfulConsumerFactory());
        return factory;
    }
}
