package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.OrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.OrderItem;

public interface OrderItemService {

    OrderItem initialize(OrderItemRequestDTO requestDTO);

    OrderItem save(OrderItem orderItem);
}
