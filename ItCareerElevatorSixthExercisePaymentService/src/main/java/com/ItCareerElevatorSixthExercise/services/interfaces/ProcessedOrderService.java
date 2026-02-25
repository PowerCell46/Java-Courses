package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProcessedOrderService {

    boolean isOrderAlreadyProcessed(ReservedOrderDTO orderDTO);

    Optional<ProcessedOrder> findByUserIdAndApproximateTotalPrice(String userId, BigDecimal totalPrice);

    void processReservedOrderCryptoPayment(ReservedOrderDTO orderDTO);

    void processReservedOrderWalletPayment(ReservedOrderDTO orderDTO, ProcessedOrder processedOrder);

    ProcessedOrder save(ProcessedOrder processedOrder);

    void sendKafkaSuccessfulOrderPayment(ProcessedOrder processedOrder);

    void sendKafkaFailureOrderPayment(ProcessedOrder processedOrder);

    ProcessedOrder initializeProcessedOrder(ReservedOrderDTO orderDTO);
}
