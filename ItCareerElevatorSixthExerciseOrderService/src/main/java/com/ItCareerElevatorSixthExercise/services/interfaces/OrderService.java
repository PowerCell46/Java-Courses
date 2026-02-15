package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.CreateOrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.Order;

public interface OrderService {

    Order save(Order order);

    CreateOrderResponseDTO create(CreateOrderRequestDTO requestDTO);
}
