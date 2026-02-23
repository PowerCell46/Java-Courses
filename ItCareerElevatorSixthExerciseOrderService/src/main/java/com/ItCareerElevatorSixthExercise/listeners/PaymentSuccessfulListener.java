package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessfulListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.topics.payment-successful}",
            groupId = "${spring.kafka.payment-successful.group-id}",
            containerFactory = "paymentSuccessfulKafkaListenerContainerFactory"
    )
    public void handlePaymentSuccessfulMessage(PaymentSuccessfulDTO paymentDTO) {
        if (paymentDTO == null || paymentDTO.getOrderId() == null)
            return;

        log.info("---> Handling paymentSuccessful for order with id {}.", paymentDTO.getOrderId());

        orderService.setStatusById(paymentDTO.getOrderId(), LoiOrderStatus.PAID);
    }
}
