package com.example.ItCareerElevatorSecondExerciseConsumer.listeners;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.ElectricityInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ElectricityInvoiceMessageListener {

    private final ElectricityInvoiceService electricityInvoiceService;

    @KafkaListener(
            topics = "electricityInvoice",
            groupId = "pdf-mailer-consumer",
            containerFactory = "invoiceKafkaListenerContainerFactory"
    )
    public void handleElectricityInvoiceMessage(ElectricityInvoiceDTO electricityInvoiceDTO) {
        log.info("--- Handling message in the 'electricityInvoice' topic.");

        if (electricityInvoiceDTO == null || electricityInvoiceDTO.getSnowflakeId() == null) {
            log.error("Invalid ElectricityInvoiceDTO: {}{}", System.lineSeparator(), electricityInvoiceDTO);
            // TODO: No error is thrown, just silenced.
            return;
        }

        log.info("Received data from Kafka: {}{}", System.lineSeparator(), electricityInvoiceDTO);

        electricityInvoiceService.sendPdfInvoiceThroughEmail(electricityInvoiceDTO);
    }
}
