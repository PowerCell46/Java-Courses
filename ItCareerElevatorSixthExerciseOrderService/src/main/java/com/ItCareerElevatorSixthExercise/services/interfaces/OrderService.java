package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.OrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.Order;

import java.math.BigDecimal;

public interface OrderService {

    Order save(Order order);

    OrderResponseDTO getById(String orderId, String userId);

    OrderResponseDTO create(OrderRequestDTO requestDTO);

    void setStatusById(Long id, Long loiOrderStatusCode);

    void processReserveItemsResult(Long orderId, BigDecimal totalPrice, Long loiOrderStatusCode);
}
