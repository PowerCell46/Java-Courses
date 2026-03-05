package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.ReserveOrderDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.reservation.ProcessedOrderService;
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
    public void handleReserveItemsMessage(ReserveOrderDTO orderDTO) {
        if (orderDTO == null || orderDTO.getId() == null)
            return;

        if (processedOrderService.isOrderProcessed(orderDTO.getId())) {
            log.warn("Order with id {} is already processed. Skipping...", orderDTO.getId());
            return;
        }

        log.info("---> Handling order with id {}.", orderDTO.getId());

        var processedOrder = processedOrderService.initializeProcessedOrder(orderDTO);
        processedOrderService.processReserveItems(orderDTO, processedOrder);
    }
}
