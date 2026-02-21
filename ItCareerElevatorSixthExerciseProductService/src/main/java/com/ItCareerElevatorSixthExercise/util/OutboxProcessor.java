package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.ReservedProductRepository;
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
    private final ReservedProductRepository reservedProductRepository;

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 2) // 2 minutes
    public void processFailedKafkaMessages() {
        List<ProcessedOrder> failedReservedOrders = fetchReservedFailedOrders();
        // TODO: You don't filter by lastModified and that can cause duplication of messages
        failedReservedOrders
                .forEach(processedOrderService::sendKafkaSuccessReserveItemsMessage);

        List<ProcessedOrder> failedNotInStockOrders = fetchNotInStockFailedOrders();
        // TODO: You don't filter by lastModified and that can cause duplication of messages
        failedNotInStockOrders
                .forEach(processedOrderService::sendKafkaFailureReserveItemsMessage);
    }

    private List<ProcessedOrder> fetchReservedFailedOrders() {
        return processedOrderRepository
                .findAllByStatus(ProcessedOrderStatus.RETRY_KAFKA_SEND);
    }

    private List<ProcessedOrder> fetchNotInStockFailedOrders() {
        return processedOrderRepository
                .findAllByStatus(ProcessedOrderStatus.NOT_IN_STOCK);
    }

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 10) // 10 minutes
    public void processFailedProcessingOrders() {
        List<ProcessedOrder> failedProcessingOrders = fetchFailedProcessingOrders();
        failedProcessingOrders
                .forEach(processedOrderService::sendKafkaFailureReserveItemsMessage);
    }

    private List<ProcessedOrder> fetchFailedProcessingOrders() {
        return processedOrderRepository
                .findAllByStatusAndLastModifiedAtBefore(
                        ProcessedOrderStatus.PROCESSING,
                        LocalDateTime.now().minusMinutes(10)
                );
    }

    @Transactional
    @Scheduled(cron = "0 48 0 * * *") // Every day at 12:48 AM
    public void cleanupOldSentToKafkaOrders() {
        List<ProcessedOrder> staleEntries = fetchOldSentToKafkaOrders();

        staleEntries
                .forEach(reservedProductRepository::deleteAllByOrder);

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
