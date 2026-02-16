package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

public interface ProcessedOrderService {

    boolean isOrderProcessed(Long orderId);

    void processReserveItems(OrderDTO orderDTO);

    ProcessedOrder save(Long orderId);
}
