package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
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
        List<ProcessedOrder> retryProcessedOrders = fetchOrdersForRetry();
    }

    private List<ProcessedOrder> fetchOrdersForRetry() {
        final Integer MIN_RETRY_TIMES = 1;
        final Integer MAX_RETRY_TIMES = 3;

        return processedOrderRepository
                .findAllByIsSentToKafkaAndRetryTimesGreaterThanEqualAndRetryTimesLessThanEqual(
                  false,
                  MIN_RETRY_TIMES,
                  MAX_RETRY_TIMES
                );
    }
}

// TODO: Целта е следната: 1. Опитай се да ритрайнеш processedOrders with retryTimes <= 3 && isSentToKafka == false
// TODO: 2. Ако има запис с над 3, го пушни към новото ентити
// TODO: 3. Направи ново ентити, което се състои от 3 колони OrderId and Reason, sentToKafka; пробвай през 2 мин да пушнеш, ако е успешно изтриваш записа от таблциата, ако не продължава да опитва докато не успее

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class OutboxProcessorImpl {
//
//    @Value("${app.kafka.topics.order}")
//    private String TOPIC_NAME;
//
//    private final OutboxEventService outboxEventService;
//    private final KafkaTemplate<String, String> orderKafkaTemplate;
//
//    @Transactional
//    @Scheduled(fixedDelay = 1000 * 60 * 2) // 2 minutes
//    public void processOutbox() {
//        log.info("ProcessOutbox call.");
//
//        List<OutboxEvent> events = outboxEventService
//                .getTop100ByOutboxStatusOrderedFromFirstToLast(OutboxStatusEnum.PENDING);
//
//        for (OutboxEvent event: events) {
//            String key = String.format("order-%s", event.getEntityId());
//            String value = event.getEntityId();
//
//            orderKafkaTemplate
//                    .send(TOPIC_NAME, key, value)
//                    .whenComplete((res, ex) -> {
//                        if (ex != null) {
//                            log.warn("Exception occurred with outboxEvent {}.", event.getId());
//                            outboxEventService.handleSendFailure(event.getId(), ex.getMessage());
//
//                        } else {
//                            log.warn("Successful write to kafka of outboxEvent {}.", event.getId());
//                            outboxEventService.markSent(event.getId());
//                        }
//                    });
//        }
//    }
//}