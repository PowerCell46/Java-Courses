package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

public interface ProcessedOrderService {

    boolean isOrderProcessed(Long orderId);

    ProcessedOrder save(ProcessedOrder processedOrder);
}
