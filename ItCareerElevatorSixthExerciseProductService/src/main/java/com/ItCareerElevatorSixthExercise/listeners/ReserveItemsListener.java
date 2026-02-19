package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveItemsListener {

    private final ProcessedOrderService processedOrderService;

    @KafkaListener(
            topics = "${app.kafka.topics.reserve-items:reserveItems}",
            groupId = "${spring.kafka.reserve-items-consumer.group-id}",
            containerFactory = "reserveItemsKafkaListenerContainerFactory"
    )
    public void handleReserveItemsMessage(OrderDTO order) {
        if (order == null || order.getId() == null)
            return;

        if (processedOrderService.isOrderProcessed(order.getId())) {
            log.warn("Order with id {} is already processed. Skipping...", order.getId());
            return;
        }

        log.info("---> Handling order with id {}.", order.getId());

        processedOrderService.processReserveItems(order);
    }
}
