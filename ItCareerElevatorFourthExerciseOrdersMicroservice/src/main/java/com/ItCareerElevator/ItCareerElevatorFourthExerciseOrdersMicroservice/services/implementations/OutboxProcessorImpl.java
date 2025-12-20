package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OutboxEventService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxProcessorImpl {

    @Value("${app.kafka.topics.order}")
    private String TOPIC_NAME;

    private final OutboxEventService outboxEventService;
    private final KafkaTemplate<String, String> orderKafkaTemplate;

    @Scheduled(fixedDelay = 1000 * 60 * 2) // 2 minutes
    @Transactional
    public void processOutbox() {
        log.info("ProcessOutbox call.");

        List<OutboxEvent> events = outboxEventService
                .getTop100ByOutboxStatusOrderedFromFirstToLast(OutboxStatusEnum.PENDING);

        for (OutboxEvent event: events) {
            String key = String.format("order-%s", event.getEntityId());
            String value = event.getEntityId();

            orderKafkaTemplate
                    .send(TOPIC_NAME, key, value)
                    .whenComplete((res, ex) -> {
                        if (ex != null) {
                            log.warn("Exception occurred with outboxEvent {}.", event.getId());
                            outboxEventService.handleSendFailure(event.getId(), ex.getMessage());

                        } else {
                            log.warn("Successful write to kafka of outboxEvent {}.", event.getId());
                            outboxEventService.markSent(event.getId());
                        }
                    });
        }
    }
}
