package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.ReserveOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;

public interface ProcessedOrderService {

    boolean isOrderProcessed(Long orderId);

    ProcessedOrder save(ProcessedOrder processedOrder);

    ProcessedOrder initializeProcessedOrder(Long orderId);

    void processReserveItems(ReserveOrderDTO reserveOrderDTO, ProcessedOrder processedOrder);

    void sendKafkaSuccessReserveItemsMessage(ProcessedOrder processedOrder);

    void sendKafkaFailureReserveItemsMessage(ProcessedOrder processedOrder);

    void processPaymentUnsuccessful(PaymentUnsuccessfulDTO paymentDTO);

    void cleanupProcessedOrder(Long id);
}
