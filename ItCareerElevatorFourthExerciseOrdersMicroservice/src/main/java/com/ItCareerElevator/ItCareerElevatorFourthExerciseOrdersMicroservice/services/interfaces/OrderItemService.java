package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request.OrderItemRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderItemService {

    Set<OrderItem> createItems(List<OrderItemRequestDTO> productIds, Order order);
}
