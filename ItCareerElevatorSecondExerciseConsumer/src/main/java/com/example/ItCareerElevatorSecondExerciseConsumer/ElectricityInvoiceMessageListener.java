package com.example.ItCareerElevatorSecondExerciseConsumer;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElectricityInvoiceMessageListener {

    @KafkaListener(
            topics = "electricityInvoice",
            groupId = "pdf-mailer-consumer",
            containerFactory = "invoiceKafkaListenerContainerFactory"
    )
    public void handleElectricityInvoiceMessage(ElectricityInvoiceDTO electricityInvoiceDTO) {
        if (electricityInvoiceDTO == null || electricityInvoiceDTO.getSnowflakeId() == null) {
            // TODO: log or throw error
            return;
        }

        System.out.println(electricityInvoiceDTO);
    }
}
