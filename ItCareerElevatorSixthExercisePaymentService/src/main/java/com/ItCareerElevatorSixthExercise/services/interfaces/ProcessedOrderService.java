package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

import java.util.Optional;

public interface ProcessedOrderService {

    Optional<ProcessedOrder> findByOrderId(Long orderId);

    void process(ReservedOrderDTO orderDTO);

    ProcessedOrder save(ProcessedOrder processedOrder);

    void sendKafkaFailureOrderPayment(ProcessedOrder processedOrder);
}
