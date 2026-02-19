package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchOrderFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.Order;
import com.ItCareerElevatorSixthExercise.entities.OrderItem;
import com.ItCareerElevatorSixthExercise.repositories.OrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.LoiOrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${app.kafka.topics.reserve-items}")
    private String RESERVE_ITEMS_TOPIC_NAME;

    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final LoiOrderStatusService loiOrderStatusService;
    private final KafkaTemplate<String, String> reserveItemsKafkaTemplate;

    @Override
    public OrderResponseDTO create(CreateOrderRequestDTO requestDTO) {
        var createdStatus = loiOrderStatusService.getByListOptionItemCode(LoiOrderStatus.CREATED);

        Order order = new Order(
                requestDTO.getUserId(),
                createdStatus,
                requestDTO
                        .getItems()
                        .stream()
                        .map(this::convertCreateOrderItemRequestDTOToOrderItem)
                        .toList()
        );

        order = save(order);
        sendKafkaReverseItemsMessage(order);

        return new OrderResponseDTO(
                order.getSnowflakeId(),
                order.getOrderStatus().getName()
        );
    }

    private OrderItem convertCreateOrderItemRequestDTOToOrderItem(CreateOrderItemRequestDTO requestDTO) {
        return new OrderItem(requestDTO.getProductId(), requestDTO.getQuantity());
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting an order of {} unique items to user with id {}.", order.getItems().size(), order.getUserId());

        return orderRepository.save(order);
    }

    private void sendKafkaReverseItemsMessage(Order order) {
        try {
            String key = String.format("reserve-items-%s", order.getUserId());
            String value = objectMapper.writeValueAsString(order);

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
            log.error("An error occurred with \"objectMapper.writeValueAsString(order)\".");
            handleKafkaMessageFailure(order);
        }
    }

    private void handleKafkaMessageFailure(Order order) {
        log.warn("Setting the order ({}) status to FAILED.", order.getId());

        order = orderRepository
                .findById(order.getId())
                .orElseThrow(() -> new IllegalStateException("Order not found."));

        var failedStatus = loiOrderStatusService.getByListOptionItemCode(LoiOrderStatus.SYSTEM_FAILURE);
        order.setOrderStatus(failedStatus);

        orderRepository.save(order);
    }

    @Override
    public OrderResponseDTO getById(String id) {
        return orderRepository
                .findById(CommonEntity.convertSnowflakeIdToId(id))
                .map(order ->
                        new OrderResponseDTO(
                                order.getSnowflakeId(),
                                order.getOrderStatus().getName()
                        ))
                .orElseThrow(() ->
                        new NoSuchOrderFoundException(String.format("No order found with id %s.", id))
                );
    }

    @Override
    public void setStatusById(Long id, Long loiOrderStatusCode) {
        Optional<Order> order = orderRepository.findById(id);

        order
                .ifPresent(value -> {
                    var orderStatus = loiOrderStatusService.getByListOptionItemCode(loiOrderStatusCode);
                    value.setOrderStatus(orderStatus);
                    orderRepository.save(order.get());
                });
    }
}
