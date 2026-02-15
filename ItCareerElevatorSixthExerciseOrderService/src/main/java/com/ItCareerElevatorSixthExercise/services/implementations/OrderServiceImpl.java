package com.ItCareerElevatorSixthExercise.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderItemRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.CreateOrderResponseDTO;
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
    public CreateOrderResponseDTO create(CreateOrderRequestDTO requestDTO) {
        Order order = new Order(
                requestDTO.getUserId(),
                loiOrderStatusService.getByListOptionItemCode(LoiOrderStatus.CREATED),
                requestDTO
                        .getItems()
                        .stream()
                        .map(this::convertCreateOrderItemRequestDTOToOrderItem)
                        .toList()
        );
        order = save(order);

        sendKafkaReverseItemsMessage(order);

        return new CreateOrderResponseDTO(
                order.getSnowflakeId(),
                order.getOrderStatus().getName()
        );
    }

    private OrderItem convertCreateOrderItemRequestDTOToOrderItem(CreateOrderItemRequestDTO requestDTO) {
        return new OrderItem(requestDTO.getProductId(), requestDTO.getQuantity());
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting order of {} items to user with id {}.", order.getItems().size(), order.getUserId());

        return orderRepository.save(order);
    }

    private void sendKafkaReverseItemsMessage(Order order) {
        try {
            String key = String.format("reserve-items-%s", order.getUserId());
            String value = objectMapper.writeValueAsString(
                    order.getItems()
            );

            reserveItemsKafkaTemplate
                    .send(RESERVE_ITEMS_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) { // TODO: This has to be avoided (make sure the bean setup retries!) If then it fails, throw some error!
                            log.error("Failed to send Order to topic {}.", RESERVE_ITEMS_TOPIC_NAME, ex);

                        } else {
                            log.info("Sent Order {} to topic {} partition {} offset {}.",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );
                        }
                    });

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // TODO: This hsa to be avoided in some way
        }
    }
}
