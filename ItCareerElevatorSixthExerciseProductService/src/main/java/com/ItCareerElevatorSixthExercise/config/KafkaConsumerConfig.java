package com.ItCareerElevatorSixthExercise.config;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentSuccessful.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.ReserveOrderDTO;
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

    @Value("${spring.kafka.reserve-items-consumer.group-id}")
    private String reserveItemsConsumerGroupId;

    @Value("${spring.kafka.payment-successful-consumer.group-id}")
    private String paymentSuccessfulConsumerGroupId;

    @Value("${spring.kafka.payment-unsuccessful-consumer.group-id}")
    private String paymentUnsuccessfulConsumerGroupId;

    @Bean
    public ConsumerFactory<String, ReserveOrderDTO> reserveItemsConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, reserveItemsConsumerGroupId);

        var keyDeserializer = new StringDeserializer();
        var valueDeserializer = new JacksonJsonDeserializer<>(ReserveOrderDTO.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                keyDeserializer,
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReserveOrderDTO> reserveItemsKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ReserveOrderDTO>();
        factory.setConsumerFactory(reserveItemsConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentSuccessfulDTO> paymentSuccessfulConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, paymentSuccessfulConsumerGroupId);

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
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, paymentUnsuccessfulConsumerGroupId);

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
