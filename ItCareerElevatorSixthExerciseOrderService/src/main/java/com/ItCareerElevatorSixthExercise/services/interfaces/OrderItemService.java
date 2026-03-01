package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.OrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.Order;
import com.ItCareerElevatorSixthExercise.entities.OrderItem;

public interface OrderItemService {

    OrderItem persistFromRequest(OrderItemRequestDTO requestDTO, Order order);

    OrderItem save(OrderItem orderItem);
}
