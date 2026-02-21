package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderItemDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

import java.util.List;

public interface ReservedProductService {

    void initializeOrderItems(List<OrderItemDTO> orderItems, ProcessedOrder processedOrder);
}
