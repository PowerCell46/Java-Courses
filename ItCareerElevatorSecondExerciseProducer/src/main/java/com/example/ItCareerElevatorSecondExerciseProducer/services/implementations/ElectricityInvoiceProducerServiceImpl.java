package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityInvoiceProducerServiceImpl implements ElectricityInvoiceProducerService {

    private final KafkaTemplate<String, ElectricityInvoice> electricityInvoiceKafkaTemplate;

    @Value("${app.kafka.topics.electricity-invoice:electricityInvoice}")
    private String topicName;

    @Override
    public void send(ElectricityInvoice electricityInvoice) {
        String key = "invoice-" + electricityInvoice.getSnowflakeId();

        electricityInvoiceKafkaTemplate
                .send(topicName, key, electricityInvoice)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send invoice {} to topic {}.", key, topicName, ex);

                    } else {
                        log.info("Sent invoice {} to topic {} partition {} offset {}.",
                                key,
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset()
                        );
                    }
                });
    }
}
