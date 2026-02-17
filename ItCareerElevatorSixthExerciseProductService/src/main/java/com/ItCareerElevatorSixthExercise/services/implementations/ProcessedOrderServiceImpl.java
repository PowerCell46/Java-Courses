package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.reserveItems.OrderItemDTO;
import com.ItCareerElevatorSixthExercise.DTOs.reservedItems.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.ProductRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedOrderServiceImpl implements ProcessedOrderService {

    @Value("${app.kafka.topics.items-reserved}")
    private String ITEMS_RESERVED_TOPIC_NAME;

    @Value("${app.kafka.topics.failure-reserve-items}")
    private String FAILURE_RESERVE_ITEMS_TOPIC_NAME;

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ProcessedOrderRepository processedOrderRepository;
    private final KafkaTemplate<String, String> itemsReservedKafkaTemplate;
    private final KafkaTemplate<String, String> failureReserveItemsKafkaTemplate;

    @Override
    public boolean isOrderProcessed(Long orderId) {
        return processedOrderRepository
                .findById(orderId)
                .isPresent();
    }

    @Override
    @Transactional
    public void processReserveItems(OrderDTO orderDTO) {
        try {
            orderDTO
                    .getOrderItems()
                    .forEach(this::reserveProduct);

            ProcessedOrder processedOrder = new ProcessedOrder(
                    orderDTO.getId(),
                    orderDTO.getUserId(),
                    calculateProductsSum(orderDTO)
            );
            processedOrder = save(processedOrder);

            sendKafkaItemsReservedMessage(processedOrder);

        } catch (DataIntegrityViolationException ex) {
            log.info("One of the items is not available - returning the other items back in stock.");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            // TODO: Send to failureReserveItems topic to make the order status FAILED
        }
    }

    private void reserveProduct(OrderItemDTO orderItemDTO) {
        productRepository
                .decreaseProductInStockQuantity(
                        orderItemDTO.getQuantity(),
                        CommonEntity.convertSnowflakeIdToId(orderItemDTO.getProductId())
                );
    }

    private BigDecimal calculateProductsSum(OrderDTO orderDTO) {
        return productRepository.getProductsPriceSum(
                orderDTO
                        .getOrderItems()
                        .stream()
                        .map(orderItemDTO ->
                                CommonEntity.convertSnowflakeIdToId(orderItemDTO.getProductId())
                        )
                        .toList()
        );
    }

    private void sendKafkaItemsReservedMessage(ProcessedOrder processedOrder) {
        try {
            ReservedOrderDTO reservedOrderDTO = new ReservedOrderDTO(
                    processedOrder.getOrderId(),
                    processedOrder.getUserId(),
                    processedOrder.getTotalPrice()
            );

            String key = String.format("items-reserved-%s", processedOrder.getUserId());
            String value = objectMapper.writeValueAsString(reservedOrderDTO);

            itemsReservedKafkaTemplate
                    .send(ITEMS_RESERVED_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send ReservedOrderDTO to topic {}.", ITEMS_RESERVED_TOPIC_NAME, ex);
                            processedOrder.setRetryTimes(processedOrder.getRetryTimes() + 1);
                            processedOrderRepository.save(processedOrder);

                        } else {
                            log.info("Sent ReservedOrderDTO {} to topic {} partition {} offset {}.",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );

                            processedOrder.setIsSentToKafka(true);
                            processedOrderRepository.save(processedOrder);
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("An error occurred with \"objectMapper.writeValueAsString(reservedOrderDTO)\".");
            processedOrder.setRetryTimes(processedOrder.getRetryTimes() + 1);
            processedOrderRepository.save(processedOrder);
        }
    }

    @Override
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        log.info("Persisting processed order with id {} to the database.", processedOrder.getOrderId());
        return processedOrderRepository.save(processedOrder);
    }
}
