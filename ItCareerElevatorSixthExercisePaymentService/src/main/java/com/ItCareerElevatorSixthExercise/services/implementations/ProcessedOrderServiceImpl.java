package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentSuccessful.PaymentSuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
    private final KafkaTemplate<String, String> orderPaymentSuccessfulKafkaTemplate;
    private final KafkaTemplate<String, String> orderPaymentUnsuccessfulKafkaTemplate;

    @Override
    public boolean isOrderAlreadyProcessed(ReservedOrderDTO orderDTO) {
        return findByOrderId(orderDTO.getOrderId())
                .isPresent();
    }

    private Optional<ProcessedOrder> findByOrderId(Long orderId) {
        return processedOrderRepository
                .findById(orderId);
    }

    @Override
    public Optional<ProcessedOrder> findByUserIdAndApproximateTotalPrice(String userId, BigDecimal totalPrice) {
        final BigDecimal ALLOWED_DEVIATION_IN_PRICE = BigDecimal.ONE;

        return processedOrderRepository
                .findByUserIdAndTotalPriceBetween(
                        userId,
                        totalPrice.subtract(ALLOWED_DEVIATION_IN_PRICE),
                        totalPrice.add(ALLOWED_DEVIATION_IN_PRICE)
                );
    }

    @Override
    public void processReservedOrder(ReservedOrderDTO orderDTO) {
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

            log.info("User with id {} hasn't specified his/hers wallet address.", processedOrder.getOrderId());
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
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        log.info("Persisting processedOrder with id {}.", processedOrder.getOrderId());
        return processedOrderRepository.save(processedOrder);
    }

    @Override
    public void sendKafkaSuccessfulOrderPayment(ProcessedOrder processedOrder) {
        try {
            var paymentSuccessfulDTO = new PaymentSuccessfulDTO(
                    processedOrder.getOrderId(),
                    processedOrder.getTotalPrice()
            );

            String key = String.format("payment-successful-%d", processedOrder.getOrderId());
            String value = objectMapper.writeValueAsString(paymentSuccessfulDTO);

            orderPaymentSuccessfulKafkaTemplate
                    .send(PAYMENT_SUCCESSFUL_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send PaymentSuccessfulDTO to topic {}.", PAYMENT_SUCCESSFUL_TOPIC_NAME, ex);

                            processedOrder.setStatus(ProcessedOrderStatus.RETRY_KAFKA_SEND);
                            processedOrderRepository.save(processedOrder);

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

            processedOrder.setStatus(ProcessedOrderStatus.RETRY_KAFKA_SEND);
            processedOrderRepository.save(processedOrder);
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

            orderPaymentUnsuccessfulKafkaTemplate
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
