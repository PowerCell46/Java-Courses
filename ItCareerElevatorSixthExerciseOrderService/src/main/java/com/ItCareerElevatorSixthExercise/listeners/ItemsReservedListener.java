package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemsReservedListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.topics.items-reserved}",
            groupId = "${spring.kafka.items-reserved-consumer.group-id}",
            containerFactory = "itemsReservedKafkaListenerContainerFactory"
    )
    public void handleItemsReservedMessage(ReservedOrderDTO orderDTO) {
        if (orderDTO == null || orderDTO.getOrderId() == null)
            return;

        log.info(""); // TODO

        orderService.setStatusById(orderDTO.getOrderId(), LoiOrderStatus.RESERVED);
    }
}
