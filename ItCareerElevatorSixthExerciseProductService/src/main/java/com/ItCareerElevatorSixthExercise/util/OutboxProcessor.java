package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.repositories.reservation.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private static final Integer MAX_TIME_FOR_A_GIVEN_STATE = 10;

    private final ProcessedOrderService processedOrderService;
    private final ProcessedOrderRepository processedOrderRepository;

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 2) // 2 minutes
    public void processFailedKafkaMessages() {
        List<ProcessedOrder> failedReservedOrders = fetchReservedFailedOrders();
        failedReservedOrders
                .forEach(processedOrderService::sendKafkaSuccessReserveItemsMessage);

        List<ProcessedOrder> failedNotInStockOrders = fetchNotInStockFailedOrders();
        failedNotInStockOrders
                .forEach(processedOrderService::sendKafkaFailureReserveItemsMessage);

        List<ProcessedOrder> failedInvalidProductsOrders = fetchInvalidProductsOrders();
        failedInvalidProductsOrders
                .forEach(processedOrderService::sendKafkaFailureReserveItemsMessage);

        List<ProcessedOrder> failedProcessingOrders = fetchFailedProcessingOrders();
        failedProcessingOrders
                .forEach(processedOrderService::sendKafkaFailureReserveItemsMessage);

        List<ProcessedOrder> failedPaidOrders = fetchPaidFailedOrders();
        failedPaidOrders
                .forEach(processedOrderService::sendKafkaOrderCompletedMessage);
    }

    private List<ProcessedOrder> fetchFailedProcessingOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.PROCESSING,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchInvalidProductsOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.INVALID_PRODUCTS,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchReservedFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.RESERVED,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchNotInStockFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.NOT_IN_STOCK,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchPaidFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.PAID,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }
}
