package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private final ProcessedOrderService processedOrderService;
    private final ProcessedOrderRepository processedOrderRepository;

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 2) // 2 minutes
    public void processFailedKafkaMessages() {
        List<ProcessedOrder> failedReservedOrders = fetchReservedFailedOrders();
        failedReservedOrders
                .forEach(processedOrderService::sendKafkaItemsReservedMessage);

        List<ProcessedOrder> failedNotInStockOrders = fetchNotInStockFailedOrders();
        failedNotInStockOrders
                .forEach(processedOrderService::sendKafkaFailureReserveItemsMessage);

        // TODO: If 5-10 minutes have passed and status is PROCESSING, call sendKafkaFailureReserveItemsMessage

        // TODO: If you add a lastModifiedAt timestamp in ProcessedOrder, you can delete the ones with
        // TODO: status SENT_TO_KAFKA and 24 hours past last modify
    }

    private List<ProcessedOrder> fetchReservedFailedOrders() {
        return processedOrderRepository
                .findAllByStatus(ProcessedOrderStatus.RETRY_KAFKA_SEND);
    }

    private List<ProcessedOrder> fetchNotInStockFailedOrders() {
        return processedOrderRepository
                .findAllByStatus(ProcessedOrderStatus.NOT_IN_STOCK);
    }
}
