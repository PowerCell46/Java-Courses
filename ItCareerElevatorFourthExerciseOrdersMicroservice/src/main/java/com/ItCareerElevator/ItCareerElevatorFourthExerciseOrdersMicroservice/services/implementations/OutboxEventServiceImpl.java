package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.OutboxEventRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OutboxEventService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OrderCreatedEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEventServiceImpl implements OutboxEventService {

    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    @Override
    public OutboxEvent constructNonPersistedOutboxEventFromOrder(Order order) {
        try {
            String payload = objectMapper.writeValueAsString(OrderCreatedEvent.from(order));

            return new OutboxEvent(
                    "Order",
                    order.getId(),
                    "OrderCreated",
                    payload,
                    OutboxStatus.PENDING,
                    null
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize OrderCreatedEvent", e); // TODO: Throw a custom ex
        }
    }

    @Override
    public OutboxEvent save(OutboxEvent outboxEvent) {
        log.info("Persisting outboxEvent (with aggregateId {}) to the database.", outboxEvent.getAggregateId());

        return outboxEventRepository.save(outboxEvent);
    }

    @Override
    public List<OutboxEvent> getTop100ByOutboxStatusOrderedFromFirstToLast(OutboxStatus outboxStatus) {
        return outboxEventRepository.findTop100ByStatusOrderByCreatedAtAsc(outboxStatus);
    }
}
