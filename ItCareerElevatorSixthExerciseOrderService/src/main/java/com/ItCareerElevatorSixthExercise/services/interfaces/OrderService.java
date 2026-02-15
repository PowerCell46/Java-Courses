package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.Order;

public interface OrderService {

    Order save(Order order);

    OrderResponseDTO create(CreateOrderRequestDTO requestDTO);

    OrderResponseDTO getById(String id);
}
