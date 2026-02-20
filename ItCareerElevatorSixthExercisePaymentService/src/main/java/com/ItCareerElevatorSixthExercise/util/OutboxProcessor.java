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
        List<ProcessedOrder>
    }

//    @Transactional
//    @Scheduled(fixedDelay = 1_000 * 60 * 60) // 1 hour
//    public void processUnpaidOrders() {
//
//    }

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
