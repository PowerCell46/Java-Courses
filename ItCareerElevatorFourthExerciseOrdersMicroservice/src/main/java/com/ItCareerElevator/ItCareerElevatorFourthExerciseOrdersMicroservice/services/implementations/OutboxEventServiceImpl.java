package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.OutboxEventRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OutboxEventService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {

    private final OutboxEventRepository outboxEventRepository;

    @Override
    public OutboxEvent constructNonPersistedOutboxEventFromOrder(Order order) {
        return new OutboxEvent(
                "Order",
                order.getId(),
                "OrderCreated",
                OutboxStatusEnum.PENDING
        );
    }

    @Override
    public OutboxEvent save(OutboxEvent outboxEvent) {
        log.info("Persisting outboxEvent (with aggregateId {}) to the database.", outboxEvent.getEntityId());

        return outboxEventRepository.save(outboxEvent);
    }

    @Override
    public List<OutboxEvent> getTop100ByOutboxStatusOrderedFromFirstToLast(OutboxStatusEnum outboxStatus) {
        return outboxEventRepository.findTop100ByStatusOrderByCreatedAtAsc(outboxStatus);
    }

    @Override
    @Transactional
    public void markSent(String id) {
        OutboxEvent event = outboxEventRepository.findById(id).orElseThrow();
        event.setStatus(OutboxStatusEnum.SENT);
        event.setProcessedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void handleSendFailure(String id, String errorMessage) {
        OutboxEvent event = outboxEventRepository
                .findById(id)
                .orElseThrow();

        event.setRetryCount(event.getRetryCount() + 1);
        event.setError(String.format("%s;%s", event.getError(), errorMessage));

        final Integer MAXIMUM_RETRIES = 5;
        if (event.getRetryCount().equals(MAXIMUM_RETRIES)) // TODO: Separate “dead letter” state
            event.setStatus(OutboxStatusEnum.FAILED);
    }
}
