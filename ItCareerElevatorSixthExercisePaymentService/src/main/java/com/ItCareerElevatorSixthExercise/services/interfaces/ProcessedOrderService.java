package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProcessedOrderService {

    boolean isOrderAlreadyProcessed(ReservedOrderDTO orderDTO);

    ProcessedOrder initializeProcessedOrder(ReservedOrderDTO orderDTO);

    void processReservedOrderCryptoWallet(ReservedOrderDTO orderDTO);

    void processReservedOrderLocalWallet(ReservedOrderDTO orderDTO, ProcessedOrder processedOrder);

    ProcessedOrder save(ProcessedOrder processedOrder);

    Optional<ProcessedOrder> findByUserIdAndApproximateTotalPrice(String userId, BigDecimal totalPrice);

    void sendKafkaSuccessfulOrderPayment(ProcessedOrder processedOrder);

    void sendKafkaFailureOrderPayment(ProcessedOrder processedOrder);
}
