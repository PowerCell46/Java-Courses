package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.listeners;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = "order",
            groupId = "order-invoice-consumer",
            containerFactory = "orderInvoiceContainerFactory"
    )
    public void handleOrderMessage(String id) {
        log.info("---> Handling message in topic order.");

        if (id == null) {
            log.error("Null orderId.");
            return;
        }

        log.info("Received orderId from Kafka: {}.", id);

        orderService.sendPdfInvoiceThroughEmail(id);
    }
}
