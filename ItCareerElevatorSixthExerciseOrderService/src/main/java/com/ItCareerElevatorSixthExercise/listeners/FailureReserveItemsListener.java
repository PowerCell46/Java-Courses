package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.FailureReserveItemsDTO;
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
            case "PROCESSING" ->
                    orderService.processReserveItemsResult(
                            failureDTO.getOrderId(),
                            failureDTO.getTotalPrice(),
                            LoiOrderStatus.INTERNAL_FAILURE
                    );
            case "INVALID_PRODUCTS" ->
                    orderService.processReserveItemsResult(
                            failureDTO.getOrderId(),
                            failureDTO.getTotalPrice(),
                            LoiOrderStatus.INVALID_PRODUCTS
                    );
            case "NOT_IN_STOCK" ->
                    orderService.processReserveItemsResult(
                            failureDTO.getOrderId(),
                            failureDTO.getTotalPrice(),
                            LoiOrderStatus.NOT_IN_STOCK
                    );
        }
    }
}
