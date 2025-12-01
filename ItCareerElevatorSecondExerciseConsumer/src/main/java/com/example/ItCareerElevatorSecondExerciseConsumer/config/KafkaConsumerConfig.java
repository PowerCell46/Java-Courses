package com.example.ItCareerElevatorSecondExerciseConsumer.config;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, ElectricityInvoiceDTO> electricityInvoiceConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "pdf-mailer-consumer"); // Any consumer instance that runs with this specific groupId will be considered part of the PDF Mailer application
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        JacksonJsonDeserializer<ElectricityInvoiceDTO> jsonDeserializer = new JacksonJsonDeserializer<>(ElectricityInvoiceDTO.class);
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ElectricityInvoiceDTO> invoiceKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ElectricityInvoiceDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(electricityInvoiceConsumerFactory());
        return factory;
    }
}
