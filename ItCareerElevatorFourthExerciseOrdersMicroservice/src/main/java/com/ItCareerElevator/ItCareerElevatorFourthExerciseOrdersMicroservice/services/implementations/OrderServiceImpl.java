package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.OrderItemResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OrderItem;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.ProductTranslation;
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

        Order order = constructNonPersistedOrder(customer);
        order = save(order);

        Set<OrderItem> orderItems = orderItemService.createItems(requestDTO.getProducts(), order);
        order.setOrderItems(orderItems);

        return constructOrderResponseDTO(order);
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting {}'s order to the database.", order.getCustomer().getUsername());

        return orderRepository.save(order);
    }

    private Order constructNonPersistedOrder(User customer) {
        return new Order(customer);
    }

    private OrderResponseDTO constructOrderResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomer().getUsername(),
                order.getOrderItems()
                        .stream()
                        .map(orderItem -> {
                            ProductTranslation productTranslation = orderItem
                                    .getProduct()
                                    .getTranslations()
                                    .stream()
                                    .filter(tr -> !tr.getIsDeleted())
                                    .findFirst()
                                    .orElse(null);

                            return new OrderItemResponseDTO(
                                    productTranslation != null ? productTranslation.getName() : null,
                                    orderItem.getQuantity(),
                                    orderItem.getSinglePrice()
                            );
                        })
                        .toList()
        );
    }
}
