package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

public interface ProcessedOrderService {

    boolean isOrderProcessed(Long orderId);

    void processReserveItems(OrderDTO orderDTO);

    void sendKafkaSuccessReserveItemsMessage(ProcessedOrder processedOrder);

    void sendKafkaFailureReserveItemsMessage(ProcessedOrder processedOrder);

    ProcessedOrder save(ProcessedOrder processedOrder);
}
