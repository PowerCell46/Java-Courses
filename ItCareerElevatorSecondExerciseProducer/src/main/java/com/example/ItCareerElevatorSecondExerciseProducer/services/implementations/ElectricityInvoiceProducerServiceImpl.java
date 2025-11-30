package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityInvoiceProducerServiceImpl implements ElectricityInvoiceProducerService {

    private final KafkaTemplate<String, String> electricityInvoiceKafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC_NAME = "electricityInvoice"; // TODO: Why hardcoded?

    @Override
    public void send(ElectricityInvoiceDTO electricityInvoice) {
        try {
            String key = String.format("invoice-%s", electricityInvoice.getSnowflakeId());
            String value = objectMapper.writeValueAsString(electricityInvoice);

            electricityInvoiceKafkaTemplate
                    .send(TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send invoice {} to topic {}", key, TOPIC_NAME, ex);

                        } else {
                            log.info("Sent invoice {} to topic {} partition {} offset {}",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("Failed to serialize ElectricityInvoice to JSON", ex);
        }
    }
}
