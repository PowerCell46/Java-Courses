package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.orderCompleted.OrderCompletedDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCompletedListener {

    private final MailService mailService;

    @KafkaListener(
            topics = "${app.kafka.topics.order-completed}",
            groupId = "${spring.kafka.order-completed.group-id}",
            containerFactory = "orderCompletedKafkaListenerContainerFactory"
    )
    public void handleOrderCompletedMessage(OrderCompletedDTO orderDTO) {
        if (orderDTO == null || orderDTO.getId() == null)
            return;

        log.info("---> Handling orderCompleted with id {}.", orderDTO.getId());

        mailService.sendOrderMail(orderDTO);
    }
}
