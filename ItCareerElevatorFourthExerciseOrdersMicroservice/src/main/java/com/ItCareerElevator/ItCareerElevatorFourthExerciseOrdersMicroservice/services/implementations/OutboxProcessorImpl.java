package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OutboxEventService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxProcessorImpl {

    @Value("${app.kafka.topics.order}")
    private String TOPIC_NAME;

    private final OutboxEventService outboxEventService;
    private final KafkaTemplate<String, String> orderKafkaTemplate;

    @Scheduled(fixedDelay = 5000) // ? How much is that?
    @Transactional
    public void processOutbox() {
        List<OutboxEvent> events = outboxEventService.getTop100ByOutboxStatusOrderedFromFirstToLast(OutboxStatus.PENDING);

        for (OutboxEvent event: events) {
            orderKafkaTemplate
                    .send(TOPIC_NAME, event.getAggregateId(), event.getPayload())
                    .whenComplete((res, ex) -> {
                        if (ex != null) {
                           log.warn("Exception occurred with OutboxEvent {}.", event.getId());
                            event.setStatus(OutboxStatus.FAILED); // TODO: So what happens to these?

                        } else {
                           log.warn("Successful write to kafka of OutboxEvent {}.", event.getId());
                            event.setStatus(OutboxStatus.SENT);
                            event.setProcessedAt(LocalDateTime.now());
                            // NO SAVE?
                        }
                    });
        }
    }
}
