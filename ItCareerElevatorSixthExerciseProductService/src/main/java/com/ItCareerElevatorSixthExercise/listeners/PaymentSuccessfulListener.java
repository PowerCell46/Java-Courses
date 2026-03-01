package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentSuccessful.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.ReservedProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessfulListener {

    private final ProcessedOrderRepository processedOrderRepository;
    private final ReservedProductRepository reservedProductRepository;

    @KafkaListener(
            topics = "${app.kafka.topics.payment-successful}",
            groupId = "${spring.kafka.payment-successful-consumer.group-id}",
            containerFactory = "paymentSuccessfulKafkaListenerContainerFactory"
    )
    @Transactional
    public void handlePaymentSuccessfulMessage(PaymentSuccessfulDTO paymentDTO) {
        if (paymentDTO == null || paymentDTO.getOrderId() == null)
            return;

        log.info("---> Handling successful payment for order with id {}.", paymentDTO.getOrderId());

        processedOrderRepository // TODO: Move out to ProcessedOrderService (same logic in outboxProcessor)
                .findById(paymentDTO.getOrderId())
                .ifPresent(processedOrder -> {
                    reservedProductRepository.deleteAllByProcessedOrder(processedOrder);
                    processedOrderRepository.delete(processedOrder);
                });
    }
}
