package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.failureReserveItems.FailureReserveItemsDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FailureReserveItemsListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.topics.failure-reserve-items}",
            groupId = "${spring.kafka.failure-reserve-items-consumer.group-id}",
            containerFactory = "failureReserveItemsKafkaListenerContainerFactory"
    )
    public void handleFailedOrderReservationOfProductsMessage(FailureReserveItemsDTO failureDTO) {
        if (failureDTO == null || failureDTO.getOrderId() == null || failureDTO.getReason() == null)
            return;

        log.info("---> Handling failureReserveItems for order with id {}.", failureDTO.getOrderId());

        switch (failureDTO.getReason()) {
            case "PROCESSING" -> orderService.setStatusById(failureDTO.getOrderId(), LoiOrderStatus.INTERNAL_FAILURE);
            case "NOT_IN_STOCK" -> orderService.setStatusById(failureDTO.getOrderId(), LoiOrderStatus.NOT_IN_STOCK);
        }
    }
}
