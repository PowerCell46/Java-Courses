package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.ReserveOrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.ReserveOrderItemDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.OrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.OrderItem;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchOrderFoundException;
import com.ItCareerElevatorSixthExercise.exceptions.NonUniqueItemsException;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ItCareerElevatorSixthExercise.DTOs.request.OrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.Order;
import com.ItCareerElevatorSixthExercise.repositories.OrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.LoiOrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${app.kafka.topics.reserve-items}")
    private String RESERVE_ITEMS_TOPIC_NAME;

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final LoiOrderStatusService loiOrderStatusService;
    private final KafkaTemplate<String, String> reserveItemsKafkaTemplate;

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        if (!areOrderItemsUnique(requestDTO)) {
            throw new NonUniqueItemsException("The items in the order must be unique.");
        }

        var createdStatus = loiOrderStatusService.getByListOptionItemCode(LoiOrderStatus.CREATED);

        Order order = new Order(requestDTO.getUserId(), requestDTO.getUserEmail(), createdStatus);
        Order persistedOrder = save(order);

        List<OrderItem> orderItems = requestDTO
                .getItems()
                .stream()
                .map(orderItem -> orderItemService.persistFromRequest(orderItem, persistedOrder))
                .toList();
        persistedOrder.setItems(orderItems);

        sendKafkaReserveItemsMessage(persistedOrder);

        return new OrderResponseDTO(
                persistedOrder.getSnowflakeId(),
                persistedOrder.getOrderStatus().getName(),
                null
        );
    }

    private boolean areOrderItemsUnique(OrderRequestDTO requestDTO) {
        return requestDTO
                .getItems()
                .stream()
                .map(OrderItemRequestDTO::getProductId)
                .collect(Collectors.toSet())
                .size() == requestDTO
                .getItems()
                .size();
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting an order of user with id {} to the database.", order.getUserId());
        return orderRepository.save(order);
    }

    private void sendKafkaReserveItemsMessage(Order order) {
        try {
            String key = String.format("reserve-items-%s", order.getUserId());
            String value = objectMapper.writeValueAsString(constructReserveOrderDTO(order));

            reserveItemsKafkaTemplate
                    .send(RESERVE_ITEMS_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send Order to topic {}.", RESERVE_ITEMS_TOPIC_NAME, ex);
                            handleKafkaMessageFailure(order);

                        } else {
                            log.info("Sent Order {} to topic {} partition {} offset {}.",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("An error occurred with \"objectMapper.writeValueAsString(constructReserveOrderDTO(order))\".");
            handleKafkaMessageFailure(order);
        }
    }

    private ReserveOrderDTO constructReserveOrderDTO(Order order) {
        return new ReserveOrderDTO(
                order.getId(),
                order.getUserId(),
                order.getUserEmail(),
                order.getItems()
                        .stream()
                        .map(item -> new ReserveOrderItemDTO(
                                item.getProductId(),
                                item.getQuantity()
                        ))
                        .toList()
        );
    }

    private void handleKafkaMessageFailure(Order order) {
        order = orderRepository
                .findById(order.getId())
                .orElseThrow(() -> new NoSuchOrderFoundException("No order not found."));

        var failedStatus = loiOrderStatusService.getByListOptionItemCode(LoiOrderStatus.INTERNAL_FAILURE);

        order.setOrderStatus(failedStatus);

        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getById(String orderId, String userId) {
        return orderRepository
                .findByIdAndUserId(CommonEntity.convertSnowflakeIdToId(orderId), userId)
                .map(order ->
                        new OrderResponseDTO(
                                order.getSnowflakeId(),
                                order.getOrderStatus().getName(),
                                order.getTotalPrice()
                        ))
                .orElseThrow(() ->
                        new NoSuchOrderFoundException(String.format("Invalid or non-existent order with id %s.", orderId))
                );
    }

    @Override
    @Transactional
    public void processReserveItemsResult(Long orderId, BigDecimal totalPrice, Long loiOrderStatusCode) {
        setStatusById(orderId, loiOrderStatusCode);

        orderRepository
                .findById(orderId)
                .ifPresent(value -> {
                    value.setTotalPrice(totalPrice);
                    orderRepository.save(value);
                });
    }

    @Override
    public void setStatusById(Long id, Long loiOrderStatusCode) {
        Optional<Order> order = orderRepository.findById(id);

        order
                .ifPresent(value -> {
                    var orderStatus = loiOrderStatusService.getByListOptionItemCode(loiOrderStatusCode);
                    value.setOrderStatus(orderStatus);
                    orderRepository.save(value);
                });
    }
}
