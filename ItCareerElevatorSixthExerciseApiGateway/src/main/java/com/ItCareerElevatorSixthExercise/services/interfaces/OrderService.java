package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.order.request.OrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.order.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO requestDTO, User user);

    OrderResponseDTO getById(String id, User user);
}
