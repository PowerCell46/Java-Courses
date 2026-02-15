package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.CreateOrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.Order;
import com.ItCareerElevatorSixthExercise.entities.OrderItem;
import com.ItCareerElevatorSixthExercise.repositories.OrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;

    @Override
    public CreateOrderResponseDTO create(CreateOrderRequestDTO requestDTO) {
        Order order = new Order(
                requestDTO.getUserId(),
                orderStatusService.getByListOptionItemCode(LoiOrderStatus.CREATED),
                requestDTO
                        .getItems()
                        .stream()
                        .map(this::convertCreateOrderItemRequestDTOToOrderItem)
                        .toList()
        );
        order = save(order);

        // Push to kafka topic the products that have to be reserved
        return null;
    }

    OrderItem convertCreateOrderItemRequestDTOToOrderItem(CreateOrderItemRequestDTO requestDTO) {
        return new OrderItem(requestDTO.getProductId(), requestDTO.getQuantity());
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting order of {} items to user with id {}.", order.getItems().size(), order.getUserId());

        return orderRepository.save(order);
    }
}
