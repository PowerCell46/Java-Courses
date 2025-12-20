package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response.OrderItemResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OrderItem;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.User;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.OrderRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OrderItemService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OrderService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OutboxEventService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final OrderItemService orderItemService;
    private final OutboxEventService outboxEventService;

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        User userCustomer = userService.getById(requestDTO.getUserId());

        Order order = new Order(userCustomer);
        order = save(order);

        Set<OrderItem> orderItems = orderItemService
                .createItems(requestDTO.getProducts(), order);
        order.setOrderItems(orderItems);

        OutboxEvent outboxEvent = outboxEventService
                .constructNonPersistedOutboxEventFromOrder(order);
        outboxEventService.save(outboxEvent);

        return constructOrderResponseDTO(order);
    }

    @Override
    @Transactional
    public Order save(Order order) {
        log.info("Persisting {}'s order to the database.", order.getCustomer().getUsername());

        return orderRepository.save(order);
    }

    private OrderResponseDTO constructOrderResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getUsername(),
                order.getOrderItems()
                        .stream()
                        .map(orderItem -> new OrderItemResponseDTO(
                                orderItem.getProduct().getId(),
                                orderItem.getSinglePrice(),
                                orderItem.getQuantity()
                        ))
                        .toList()
        );
    }
}
