package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request.OrderItemRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OrderItem;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.OrderItemRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OrderItemService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<OrderItem> createItems(List<OrderItemRequestDTO> orderItemRequestDTOs, Order order) {
        Set<OrderItem> orderItems = orderItemRequestDTOs
                .stream()
                .map(orderItemRequestDTO -> {
                    Product product = productService
                            .getById(orderItemRequestDTO.getProductId());

                    return constructNonPersistedOrderItem(
                            product,
                            order,
                            orderItemRequestDTO.getQuantity()
                    );
                })
                .collect(Collectors.toSet());

        log.info("Persisting {} orderItems (for order {}) to the database.", orderItems.size(), order.getId());
        orderItemRepository.saveAll(orderItems);

        return orderItems;
    }

    private OrderItem constructNonPersistedOrderItem(Product product, Order order, Integer quantity) {
        return new OrderItem(
                product.getPrice(),
                quantity,
                product,
                order
        );
    }
}
