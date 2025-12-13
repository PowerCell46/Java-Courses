package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.OrderItemResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OrderItem;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.User;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.OrderRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OrderItemService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OrderService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        User customer = userService.getById(requestDTO.getCustomerId());

        Set<OrderItem> orderItems = orderItemService.createItems(requestDTO.getProducts());

        Order order = constructNonPersistedOrder(customer, orderItems);
        order = save(order);

        return constructOrderResponseDTO(order);
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting {}'s order of {} products.", order.getCustomer().getUsername(), order.getOrderItems().size());

        return orderRepository.save(order);
    }

    private Order constructNonPersistedOrder(User customer, Set<OrderItem> orderItems) {
        return new Order(customer, orderItems);
    }

    private OrderResponseDTO constructOrderResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getUsername()
//                ,
//                order.getOrderItems()
//                        .stream()
//                        .map(orderItem -> new OrderItemResponseDTO(orderItem.getProduct().getTranslations()))
//                        .toList()
        );
    }
}
