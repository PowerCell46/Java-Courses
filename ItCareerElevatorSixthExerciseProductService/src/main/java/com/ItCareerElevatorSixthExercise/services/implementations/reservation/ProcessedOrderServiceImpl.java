package com.ItCareerElevatorSixthExercise.services.implementations.reservation;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.failureReserveItems.FailureReserveItemsDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.orderCompleted.OrderCompletedDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.orderCompleted.OrderItemCompletedDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.ReserveOrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.ReserveOrderItemDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.reservation.ReservedProduct;
import com.ItCareerElevatorSixthExercise.repositories.reservation.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.product.ProductRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderPersistenceService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ReservedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedOrderServiceImpl implements ProcessedOrderService {

    @Value("${app.kafka.topics.items-reserved}")
    private String ITEMS_RESERVED_TOPIC_NAME;

    @Value("${app.kafka.topics.failure-reserve-items}")
    private String FAILURE_RESERVE_ITEMS_TOPIC_NAME;

    @Value("${app.kafka.topics.order-completed}")
    private String ORDER_COMPLETED_TOPIC_NAME;

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ReservedProductService reservedProductService;
    private final ProcessedOrderRepository processedOrderRepository;
    private final KafkaTemplate<String, String> itemsReservedKafkaTemplate;
    private final KafkaTemplate<String, String> orderCompletedKafkaTemplate;
    private final KafkaTemplate<String, String> failureReserveItemsKafkaTemplate;
    private final ProcessedOrderPersistenceService processedOrderPersistenceService;

    @Override
    public boolean isOrderProcessed(Long orderId) {
        return processedOrderRepository
                .findById(orderId)
                .isPresent();
    }

    @Override
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        log.info("Persisting processed order with id {} to the database.", processedOrder.getOrderId());
        return processedOrderRepository.save(processedOrder);
    }

    @Override
    public ProcessedOrder initializeProcessedOrder(ReserveOrderDTO reserveOrderDTO) {
        ProcessedOrder processedOrder = new ProcessedOrder(
                reserveOrderDTO.getId(),
                reserveOrderDTO.getUserId(),
                reserveOrderDTO.getUserEmail(),
                ProcessedOrderStatus.PROCESSING
        );
        return save(processedOrder);
    }

    @Override
    @Transactional
    public void processReserveItems(ReserveOrderDTO reserveOrderDTO, ProcessedOrder processedOrder) {
        if (!areOrderItemsValid(reserveOrderDTO.getOrderItems())) {
            processedOrder.setStatus(ProcessedOrderStatus.INVALID_PRODUCTS);
            final ProcessedOrder failedOrder = processedOrderRepository.save(processedOrder);

            TransactionSynchronizationManager
                    .registerSynchronization(new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            sendKafkaFailureReserveItemsMessage(failedOrder);
                        }
                    });
        }

        processedOrder.setUserId(reserveOrderDTO.getUserId());
        processedOrder.setTotalPrice(calculateProductsSum(reserveOrderDTO));

        try {
            reserveOrderDTO
                    .getOrderItems()
                    .forEach(this::reserveProduct);

            processedOrder.setStatus(ProcessedOrderStatus.RESERVED);
            final ProcessedOrder savedOrder = processedOrderRepository.save(processedOrder);

            reservedProductService.initializeOrderItems(reserveOrderDTO.getOrderItems(), savedOrder);

            log.info("Successful reservation of products for order with id {}.", reserveOrderDTO.getId());

            TransactionSynchronizationManager
                    .registerSynchronization(new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            sendKafkaSuccessReserveItemsMessage(savedOrder);
                        }
                    });

        } catch (DataIntegrityViolationException ex) {
            log.info("One of the items is not available. Returning the other items back in stock.");

            processedOrder.setStatus(ProcessedOrderStatus.NOT_IN_STOCK);
            final ProcessedOrder failedOrder = processedOrderPersistenceService.save(processedOrder);

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            TransactionSynchronizationManager
                    .registerSynchronization(new TransactionSynchronization() {
                        @Override
                        public void afterCompletion(int status) {
                            if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                                sendKafkaFailureReserveItemsMessage(failedOrder);
                            }
                        }
                    });
        }
    }

    private boolean areOrderItemsValid(List<ReserveOrderItemDTO> orderItems) {
        List<Long> productIds = orderItems
                .stream()
                .map(reserveOrderItemDTO -> CommonEntity.convertSnowflakeIdToId(reserveOrderItemDTO.getProductId()))
                .toList();

        long validIdsCount = productRepository.findAllById(productIds).size();
        return validIdsCount == productIds.size();
    }

    private BigDecimal calculateProductsSum(ReserveOrderDTO reserveOrderDTO) {
        return productRepository
                .getProductsPriceSum(
                        reserveOrderDTO
                                .getOrderItems()
                                .stream()
                                .map(reserveOrderItemDTO ->
                                        CommonEntity.convertSnowflakeIdToId(reserveOrderItemDTO.getProductId())
                                )
                                .toList()
                );
    }

    private void reserveProduct(ReserveOrderItemDTO reserveOrderItemDTO) {
        productRepository
                .decreaseProductInStockQuantity(
                        reserveOrderItemDTO.getQuantity(),
                        CommonEntity.convertSnowflakeIdToId(reserveOrderItemDTO.getProductId())
                );
    }

    @Override
    public void sendKafkaSuccessReserveItemsMessage(ProcessedOrder processedOrder) {
        try {
            var reservedOrderDTO = new ReservedOrderDTO(
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

                        } else {
                            log.info("Sent ReservedOrderDTO {} to topic {}; partition {}; offset {}.",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );

                            processedOrder.setStatus(ProcessedOrderStatus.SENT_TO_KAFKA);
                            processedOrderRepository.save(processedOrder);
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("An error occurred with \"objectMapper.writeValueAsString(reservedOrderDTO)\".");
        }
    }

    @Override
    public void sendKafkaFailureReserveItemsMessage(ProcessedOrder processedOrder) {
        try {
            var failureReserveItemsDTO = new FailureReserveItemsDTO(
                    processedOrder.getOrderId(),
                    processedOrder.getTotalPrice(),
                    processedOrder.getStatus().name()
            );

            String key = String.format("failure-reserve-items-%s", processedOrder.getUserId());
            String value = objectMapper.writeValueAsString(failureReserveItemsDTO);

            failureReserveItemsKafkaTemplate
                    .send(FAILURE_RESERVE_ITEMS_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send FailureReserveItemsDTO to topic {}.", FAILURE_RESERVE_ITEMS_TOPIC_NAME, ex);

                        } else {
                            log.info("Sent FailureReserveItemsDTO {} to topic {}; partition {}; offset {}.",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );

                            processedOrder.setStatus(ProcessedOrderStatus.SENT_TO_KAFKA);
                            processedOrderRepository.save(processedOrder);
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("An error occurred with \"objectMapper.writeValueAsString(failureReserveItemsDTO)\".");
        }
    }

    @Override
    public void sendKafkaOrderCompletedMessage(ProcessedOrder processedOrder) {
        try {
            var orderDTO = constructOrderCompleted(processedOrder);

            String key = String.format("order-completed-%s", orderDTO.getId());
            String value = objectMapper.writeValueAsString(orderDTO);

            orderCompletedKafkaTemplate
                    .send(ORDER_COMPLETED_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send OrderCompletedDTO to topic {}.", ORDER_COMPLETED_TOPIC_NAME);

                        } else {
                            log.info("Sent OrderCompletedDTO {} to topic {}; partition {}; offset {}.",
                                    key,
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset()
                            );
                            processedOrderPersistenceService.deleteById(processedOrder.getOrderId());
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.info("An error occurred with \"objectMapper.writeValueAsString(orderCompletedDTO)\".");
        }
    }

    private OrderCompletedDTO constructOrderCompleted(ProcessedOrder processedOrder) {
        return new OrderCompletedDTO(
                CommonEntity.convertIdToSnowflakeId(processedOrder.getOrderId()),
                processedOrder.getUserEmail(),
                processedOrder.getTotalPrice(),
                processedOrder
                        .getReservedProducts()
                        .stream()
                        .map(reservedProduct -> new OrderItemCompletedDTO(
                                        CommonEntity.convertIdToSnowflakeId(reservedProduct.getProduct().getId()),
                                        reservedProduct.getQuantity(),
                                        reservedProduct.getProduct().getPrice()
                                )
                        )
                        .toList()
        );
    }

    @Override
    @Transactional
    public void processPaymentSuccessful(Long id) {
        processedOrderRepository
                .findById(id)
                .ifPresent(processedOrder -> {
                    processedOrder.setStatus(ProcessedOrderStatus.PAID);
                    processedOrder = processedOrderRepository.save(processedOrder);

                    sendKafkaOrderCompletedMessage(processedOrder);
                });
    }

    @Override
    @Transactional
    public void processPaymentUnsuccessful(PaymentUnsuccessfulDTO paymentDTO) {
        processedOrderRepository
                .findById(paymentDTO.getOrderId())
                .ifPresent(processedOrder -> {
                    List<ReservedProduct> reservedProducts = reservedProductService.getAllByProcessedOrder(processedOrder);

                    reservedProducts
                            .forEach(this::returnBackProduct);

                    cleanupProcessedOrder(paymentDTO.getOrderId());
                });
    }

    @Override
    @Transactional
    public void cleanupProcessedOrder(Long id) {
        log.info("Deleting processedOrder with id {} and its reservedProducts from the database.", id);
        productRepository.deleteById(id);
    }

    private void returnBackProduct(ReservedProduct reservedProduct) {
        productRepository
                .increaseProductInStockQuantity(
                        reservedProduct.getQuantity(),
                        reservedProduct.getProduct().getId()
                );
    }
}
