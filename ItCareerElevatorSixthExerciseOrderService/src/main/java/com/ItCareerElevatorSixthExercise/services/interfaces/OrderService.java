package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.FailureReserveItemsDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.OrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.Order;

public interface OrderService {

    Order save(Order order);

    OrderResponseDTO create(OrderRequestDTO requestDTO);

    OrderResponseDTO getById(String id);

    void processReservedOrder(ReservedOrderDTO orderDTO);

    void processFailureReserveItems(FailureReserveItemsDTO failureDTO, Long loiOrderStatusCode);

    void setStatusById(Long id, Long loiOrderStatusCode);
}
