package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentUnsuccessfulListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.topics.payment-unsuccessful}",
            groupId = "${spring.kafka.payment-unsuccessful.group-id}",
            containerFactory = "paymentUnsuccessfulKafkaListenerContainerFactory"
    )
    public void handlePaymentUnsuccessfulMessage(PaymentUnsuccessfulDTO paymentDTO) {
        if (paymentDTO == null || paymentDTO.getOrderId() == null)
            return;

        log.info("---> Handling paymentUnsuccessful for order with id {}.", paymentDTO.getOrderId());

        switch (paymentDTO.getReason()) {
            case "MISSING_WALLET_ADDRESS" -> orderService.setStatusById(paymentDTO.getOrderId(), LoiOrderStatus.MISSING_WALLET_ADDRESS);
            case "TIMED_OUT" -> orderService.setStatusById(paymentDTO.getOrderId(), LoiOrderStatus.UNPAID);
            case "INSUFFICIENT_BALANCE" -> orderService.setStatusById(paymentDTO.getOrderId(), LoiOrderStatus.INSUFFICIENT_BALANCE);
        }
    }
}
