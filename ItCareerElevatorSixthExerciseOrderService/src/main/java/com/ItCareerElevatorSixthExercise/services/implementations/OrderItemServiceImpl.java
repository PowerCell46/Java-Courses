package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.OrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.Order;
import com.ItCareerElevatorSixthExercise.entities.OrderItem;
import com.ItCareerElevatorSixthExercise.repositories.OrderItemRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem constructFromRequest(OrderItemRequestDTO requestDTO, Order order) {
        OrderItem orderItem = new OrderItem(
                requestDTO.getProductId(),
                requestDTO.getQuantity(),
                order
        );

        return save(orderItem);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        log.info("Persisting orderItem with product id {} and quantity {} to the database.", orderItem.getProductId(), orderItem.getQuantity());

        return orderItemRepository.save(orderItem);
    }
}
