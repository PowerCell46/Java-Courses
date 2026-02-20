package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

import java.util.Optional;

public interface ProcessedOrderService {

    Optional<ProcessedOrder> findByOrderId(Long orderId);
}
