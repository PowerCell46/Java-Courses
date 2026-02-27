package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.failureReserveItems.FailureReserveItemsDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderItemDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ReservedProduct;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.ProductRepository;
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

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ReservedProductService reservedProductService;
    private final ProcessedOrderRepository processedOrderRepository;
    private final KafkaTemplate<String, String> itemsReservedKafkaTemplate;
    private final KafkaTemplate<String, String> failureReserveItemsKafkaTemplate;
    private final ProcessedOrderPersistenceService processedOrderPersistenceService;

    @Override
    public boolean isOrderProcessed(Long orderId) {
        return processedOrderRepository
                .findById(orderId)
                .isPresent();
    }

    @Override
    @Transactional
    public void processReserveItems(OrderDTO orderDTO, ProcessedOrder processedOrder) {
        try {
            orderDTO
                    .getOrderItems()
                    .forEach(this::reserveProduct);

            processedOrder.setUserId(orderDTO.getUserId());
            processedOrder.setTotalPrice(calculateProductsSum(orderDTO));
            processedOrder.setStatus(ProcessedOrderStatus.RESERVED);
            final ProcessedOrder savedOrder = processedOrderRepository.save(processedOrder);

            reservedProductService.initializeOrderItems(orderDTO.getOrderItems(), savedOrder);

            log.info("Successful reservation of products for order with id {}.", orderDTO.getId());

            TransactionSynchronizationManager
                    .registerSynchronization(new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            sendKafkaSuccessReserveItemsMessage(savedOrder);
                        }
                    });

        } catch (DataIntegrityViolationException ex) {
            log.info("One of the items is not available - returning the other items back in stock.");

            // TODO: WHY ARE YOU CREATING A NEW ENTRY???
            final ProcessedOrder failedOrder = processedOrderPersistenceService.saveNotInStock(orderDTO.getId());

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

    @Override
    public ProcessedOrder initializeProcessedOrder(Long orderId) {
        return save(new ProcessedOrder(orderId, ProcessedOrderStatus.PROCESSING));
    }

    private void reserveProduct(OrderItemDTO orderItemDTO) {
        productRepository
                .decreaseProductInStockQuantity(
                        orderItemDTO.getQuantity(),
                        CommonEntity.convertSnowflakeIdToId(orderItemDTO.getProductId())
                );
    }

    private BigDecimal calculateProductsSum(OrderDTO orderDTO) {
        return productRepository
                .getProductsPriceSum(
                        orderDTO
                                .getOrderItems()
                                .stream()
                                .map(orderItemDTO ->
                                        CommonEntity.convertSnowflakeIdToId(orderItemDTO.getProductId())
                                )
                                .toList()
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
            System.out.println("ITEMS RESERVED: " + value);

            itemsReservedKafkaTemplate
                    .send(ITEMS_RESERVED_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send ReservedOrderDTO to topic {}.", ITEMS_RESERVED_TOPIC_NAME, ex);

                            processedOrder.setStatus(ProcessedOrderStatus.RETRY_KAFKA_SEND);
                            processedOrderRepository.save(processedOrder);

                        } else {
                            log.info("Sent ReservedOrderDTO {} to topic {} partition {} offset {}.",
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

            processedOrder.setStatus(ProcessedOrderStatus.RETRY_KAFKA_SEND);
            processedOrderRepository.save(processedOrder);
        }
    }

    @Override
    public void sendKafkaFailureReserveItemsMessage(ProcessedOrder processedOrder) {
        try {
            var failureReserveItemsDTO = new FailureReserveItemsDTO(
                    processedOrder.getOrderId(),
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
                            log.info("Sent FailureReserveItemsDTO {} to topic {} partition {} offset {}.",
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
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        log.info("Persisting processed order with id {} to the database.", processedOrder.getOrderId());
        return processedOrderRepository.save(processedOrder);
    }

    @Override
    public void processPaymentUnsuccessful(PaymentUnsuccessfulDTO paymentDTO) {
        ProcessedOrder processedOrder = processedOrderRepository
                .findById(paymentDTO.getOrderId())
                .get();

        List<ReservedProduct> reservedProducts = reservedProductService
                .getAllByProcessedOrder(processedOrder);

        reservedProducts
                .forEach(this::returnBackProduct);

        processedOrderRepository.delete(processedOrder);
    }

    public void returnBackProduct(ReservedProduct reservedProduct) {
        productRepository
                .increaseProductInStockQuantity(
                        reservedProduct.getQuantity(),
                        reservedProduct.getProduct().getId()
                );
    }
}
