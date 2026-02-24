package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.paymentUnsuccessful.PaymentUnsuccessfulDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;

public interface ProcessedOrderService {

    boolean isOrderProcessed(Long orderId);

    ProcessedOrder initializeProcessedOrder(Long orderId);

    void processReserveItems(OrderDTO orderDTO, ProcessedOrder processedOrder);

    void sendKafkaSuccessReserveItemsMessage(ProcessedOrder processedOrder);

    void sendKafkaFailureReserveItemsMessage(ProcessedOrder processedOrder);

    ProcessedOrder save(ProcessedOrder processedOrder);

    void processPaymentUnsuccessful(PaymentUnsuccessfulDTO paymentDTO);
}
