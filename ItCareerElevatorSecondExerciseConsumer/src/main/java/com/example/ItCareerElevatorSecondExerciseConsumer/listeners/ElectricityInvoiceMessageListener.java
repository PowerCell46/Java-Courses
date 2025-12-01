package com.example.ItCareerElevatorSecondExerciseConsumer.listeners;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.ElectricityInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElectricityInvoiceMessageListener {

    private final ElectricityInvoiceService electricityInvoiceService;

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
        // TODO: Do i need to validate the upcoming data?

        System.out.println(electricityInvoiceDTO);

        electricityInvoiceService.sendInvoice(electricityInvoiceDTO);
    }
}
