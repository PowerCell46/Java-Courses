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

    private final ProcessedOrderService processedOrderService;
    private final ProcessedOrderRepository processedOrderRepository;

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 2) // 2 minutes
    public void processFailedKafkaMessages() {
        List<ProcessedOrder> ordersWithMissingWalletAddress = fetchOrdersWithMissingWalletAddress();
        ordersWithMissingWalletAddress
                .forEach(processedOrderService::sendKafkaFailureOrderPayment);

        List<ProcessedOrder> retryPaidFailedInKafkaOrders = fetchPaidOrdersFailedInKafka();
        retryPaidFailedInKafkaOrders
                .forEach(processedOrderService::sendKafkaSuccessfulOrderPayment);
    }

    private List<ProcessedOrder> fetchOrdersWithMissingWalletAddress() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.MISSING_WALLET_ADDRESS,
                        LocalDateTime.now().minusMinutes(4)
                );
    }

    private List<ProcessedOrder> fetchPaidOrdersFailedInKafka() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.RETRY_KAFKA_SEND,
                        LocalDateTime.now().minusMinutes(4)
                );
    }

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 60) // 1 hour
    public void processUnpaidOrders() {
        List<ProcessedOrder> timedOutProcessingOrders = fetchTimedOutProcessingOrders();
        timedOutProcessingOrders = timedOutProcessingOrders
                .stream()
                .peek(processedOrder -> processedOrder.setStatus(ProcessedOrderStatus.TIMED_OUT))
                .toList();

        processedOrderRepository.saveAll(timedOutProcessingOrders);
        timedOutProcessingOrders
                .forEach(processedOrderService::sendKafkaFailureOrderPayment);
    }

    private List<ProcessedOrder> fetchTimedOutProcessingOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.PROCESSING,
                        LocalDateTime.now().minusHours(1)
                );
    }

    @Transactional
    @Scheduled(cron = "1 23 0 * * *") // Every day at 01:23 AM
    public void cleanupOldSentToKafkaOrders() {
        List<ProcessedOrder> staleEntries = fetchOldSentToKafkaOrders();

        log.info("Daily cleanup of stale processed orders [{}]", staleEntries.size());
        processedOrderRepository.deleteAll(staleEntries);
    }

    private List<ProcessedOrder> fetchOldSentToKafkaOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.SENT_TO_KAFKA,
                        LocalDateTime.now().minusDays(1)
                );
    }
}
