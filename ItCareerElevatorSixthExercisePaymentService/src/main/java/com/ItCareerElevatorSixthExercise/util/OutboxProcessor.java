package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
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
        List<ProcessedOrder> failedProcessingOrders = fetchProcessingFailedOrders();
        failedProcessingOrders
                .forEach(processedOrderService::sendKafkaFailureOrderPayment);

        /*
        List<ProcessedOrder> failedMissingWalletAddressOrders = fetchMissingWalletAddressFailedOrders();
        failedMissingWalletAddressOrders
                .forEach(processedOrderService::sendKafkaFailureOrderPayment);
        */

        /*
        List<ProcessedOrder> timedOutOrders = fetchTimedOutFailedOrders();
        timedOutOrders
                .forEach(processedOrderService::sendKafkaFailureOrderPayment);
         */

        List<ProcessedOrder> failedInsufficientBalanceOrders = fetchInsufficientBalanceFailedOrders();
        failedInsufficientBalanceOrders
                .forEach(processedOrderService::sendKafkaFailureOrderPayment);

        List<ProcessedOrder> retryPaidFailedInKafkaOrders = fetchPaidFailedOrders();
        retryPaidFailedInKafkaOrders
                .forEach(processedOrderService::sendKafkaSuccessfulOrderPayment);
    }

    private List<ProcessedOrder> fetchProcessingFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.PROCESSING,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchMissingWalletAddressFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.MISSING_WALLET_ADDRESS,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchTimedOutFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.TIMED_OUT,
                        LocalDateTime.now().minusMinutes(MAX_TIME_FOR_A_GIVEN_STATE)
                );
    }

    private List<ProcessedOrder> fetchInsufficientBalanceFailedOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.INSUFFICIENT_BALANCE,
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
