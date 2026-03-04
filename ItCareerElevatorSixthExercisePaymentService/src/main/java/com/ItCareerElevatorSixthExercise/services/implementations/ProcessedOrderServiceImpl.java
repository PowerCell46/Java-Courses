package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderPersistenceService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedOrderServiceImpl implements ProcessedOrderService {

    @Value("${app.kafka.topics.payment-successful}")
    private String PAYMENT_SUCCESSFUL_TOPIC_NAME;

    @Value("${app.kafka.topics.payment-unsuccessful}")
    private String PAYMENT_UNSUCCESSFUL_TOPIC_NAME;

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ProcessedOrderRepository processedOrderRepository;
    private final KafkaTemplate<String, String> paymentSuccessfulKafkaTemplate;
    private final KafkaTemplate<String, String> paymentUnsuccessfulKafkaTemplate;
    private final ProcessedOrderPersistenceService processedOrderPersistenceService;

    @Override
    public boolean isOrderAlreadyProcessed(ReservedOrderDTO orderDTO) {
        return processedOrderRepository
                .findById(orderDTO.getOrderId())
                .isPresent();
    }

    @Override
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        log.info("Persisting processedOrder with id {}.", processedOrder.getOrderId());
        return processedOrderRepository.save(processedOrder);
    }

    @Override
    public ProcessedOrder initializeProcessedOrder(ReservedOrderDTO orderDTO) {
        ProcessedOrder processedOrder = new ProcessedOrder(
                orderDTO.getOrderId(),
                orderDTO.getUserId(),
                orderDTO.getTotalPrice(),
                ProcessedOrderStatus.PROCESSING
        );

        return save(processedOrder);
    }

    @Override
    @Transactional
    public void processReservedOrderLocalWallet(ReservedOrderDTO orderDTO, ProcessedOrder processedOrder) {
        try {
            userRepository.payForOrder(orderDTO.getUserId(), orderDTO.getTotalPrice());

            processedOrder.setStatus(ProcessedOrderStatus.PAID);
            final ProcessedOrder savedOrder = processedOrderRepository.save(processedOrder);

            log.info("Successful payment of order with id {}.", processedOrder.getOrderId());

            TransactionSynchronizationManager
                    .registerSynchronization(new TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            sendKafkaSuccessfulOrderPayment(savedOrder);
                        }
                    });

        } catch (DataIntegrityViolationException ex) {
            log.info("Insufficient balance to pay for order with id {}.", orderDTO.getOrderId());

            processedOrder.setStatus(ProcessedOrderStatus.INSUFFICIENT_BALANCE);
            final ProcessedOrder failedOrder = processedOrderPersistenceService.save(processedOrder);

            sendKafkaFailureOrderPayment(failedOrder);
        }
    }

    @Override
    public Optional<ProcessedOrder> findByUserIdAndApproximateTotalPrice(String userId, BigDecimal totalPrice) {
        final BigDecimal ALLOWED_DEVIATION_IN_EURO_PRICE = BigDecimal.valueOf(2L);

        return processedOrderRepository
                .findByUserIdAndTotalPriceBetween(
                        userId,
                        totalPrice.subtract(ALLOWED_DEVIATION_IN_EURO_PRICE),
                        totalPrice.add(ALLOWED_DEVIATION_IN_EURO_PRICE)
                );
    }

    @Override
    public void processReservedOrderCryptoWallet(ReservedOrderDTO orderDTO) {
        if (isWalletPresent(orderDTO)) {
            ProcessedOrder processedOrder = new ProcessedOrder(
                    orderDTO.getOrderId(),
                    orderDTO.getUserId(),
                    orderDTO.getTotalPrice(),
                    ProcessedOrderStatus.PROCESSING
            );
            save(processedOrder);

        } else {
            ProcessedOrder processedOrder = new ProcessedOrder(
                    orderDTO.getOrderId(),
                    orderDTO.getUserId(),
                    orderDTO.getTotalPrice(),
                    ProcessedOrderStatus.MISSING_WALLET_ADDRESS
            );
            processedOrder = save(processedOrder);

            log.info("User with id {} hasn't specified their wallet address.", processedOrder.getOrderId());
            sendKafkaFailureOrderPayment(processedOrder);
        }
    }

    private boolean isWalletPresent(ReservedOrderDTO orderDTO) {
        return userRepository
                .findById(orderDTO.getUserId())
                .map(User::getWalletAddress)
                .filter(addr -> !addr.isBlank())
                .isPresent();
    }

    @Override
    public void sendKafkaSuccessfulOrderPayment(ProcessedOrder processedOrder) {
        try {
            var paymentSuccessfulDTO = new PaymentSuccessfulDTO(processedOrder.getOrderId());

            String key = String.format("payment-successful-%d", processedOrder.getOrderId());
            String value = objectMapper.writeValueAsString(paymentSuccessfulDTO);

            paymentSuccessfulKafkaTemplate
                    .send(PAYMENT_SUCCESSFUL_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send PaymentSuccessfulDTO to topic {}.", PAYMENT_SUCCESSFUL_TOPIC_NAME, ex);

                        } else {
                            log.info("Sent PaymentSuccessfulDTO {} to topic {} partition {} offset {}.",
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
            log.error("An error occurred with \"objectMapper.writeValueAsString(paymentSuccessfulDTO)\".");
        }
    }

    @Override
    public void sendKafkaFailureOrderPayment(ProcessedOrder processedOrder) {
        try {
            var paymentUnsuccessfulDTO = new PaymentUnsuccessfulDTO(
                    processedOrder.getOrderId(),
                    processedOrder.getStatus().name()
            );

            String key = String.format("payment-unsuccessful-%d", paymentUnsuccessfulDTO.getOrderId());
            String value = objectMapper.writeValueAsString(paymentUnsuccessfulDTO);

            paymentUnsuccessfulKafkaTemplate
                    .send(PAYMENT_UNSUCCESSFUL_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send PaymentUnsuccessfulDTO to topic {}.", PAYMENT_UNSUCCESSFUL_TOPIC_NAME, ex);

                        } else {
                            log.info("Sent PaymentUnsuccessfulDTO {} to topic {} partition {} offset {}.",
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
            log.error("An error occurred with \"objectMapper.writeValueAsString(paymentUnsuccessfulDTO)\".");
        }
    }
}
