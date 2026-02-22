package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentUnsuccessfulListener {

    private final ProcessedOrderService processedOrderService;

    @KafkaListener(
            topics = "${app.kafka.topics.payment-unsuccessful}",
            groupId = "${spring.kafka.payment-unsuccessful-consumer.group-id}",
            containerFactory = "paymentUnsuccessfulKafkaListenerContainerFactory"
    )
    public void handlePaymentUnsuccessfulMessage(PaymentUnsuccessfulDTO paymentDTO) {
        if (paymentDTO == null || paymentDTO.getOrderId() == null)
            return;

        log.info("---> Handling unsuccessful payment for oder with id {}.", paymentDTO.getOrderId());

        processedOrderService.processPaymentUnsuccessful(paymentDTO);
    }
}
