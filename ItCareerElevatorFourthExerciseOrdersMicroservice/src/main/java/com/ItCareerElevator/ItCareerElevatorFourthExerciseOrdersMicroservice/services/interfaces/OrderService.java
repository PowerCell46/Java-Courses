package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;

public interface OrderService {

    OrderResponseDTO create(OrderRequestDTO requestDTO);

    Order save(Order order);
}
