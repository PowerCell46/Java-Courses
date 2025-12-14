package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.response.OrderResponseDTO;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO requestDTO);
}
