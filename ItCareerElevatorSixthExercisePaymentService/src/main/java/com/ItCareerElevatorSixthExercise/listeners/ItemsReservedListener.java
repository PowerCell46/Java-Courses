package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.PaymentService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemsReservedListener {

    private final PaymentService paymentService;
    private final ProcessedOrderService processedOrderService;

    @KafkaListener(
            topics = "${app.kafka.topics.items-reserved}",
            groupId = "${spring.kafka.items-reserved-consumer.group-id}",
            containerFactory = "itemsReservedKafkaListenerContainerFactory"
    )
    public void handleItemsReserved(ReservedOrderDTO orderDTO) {
        if (orderDTO == null || orderDTO.getOrderId() == null)
            return;

        if (isOrderAlreadyProcessed(orderDTO)) {
            log.info("Order with id {} is already processed. Skipping...", orderDTO.getOrderId());
            return;
        }

//        paymentService.
    }

    private boolean isOrderAlreadyProcessed(ReservedOrderDTO orderDTO) {
        return processedOrderService
                .findByOrderId(orderDTO.getOrderId())
                .isPresent();
    }
}
