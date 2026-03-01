package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentSuccessful.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessfulListener {

    private ProcessedOrderService processedOrderService;

    @KafkaListener(
            topics = "${app.kafka.topics.payment-successful}",
            groupId = "${spring.kafka.payment-successful-consumer.group-id}",
            containerFactory = "paymentSuccessfulKafkaListenerContainerFactory"
    )
    public void handlePaymentSuccessfulMessage(PaymentSuccessfulDTO paymentDTO) {
        if (paymentDTO == null || paymentDTO.getOrderId() == null)
            return;

        log.info("---> Handling successful payment for order with id {}.", paymentDTO.getOrderId());

        processedOrderService.cleanupProcessedOrder(paymentDTO.getOrderId());
    }
}
