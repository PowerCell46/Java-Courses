package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatus;

import java.util.List;

public interface OutboxEventService {

    OutboxEvent constructNonPersistedOutboxEventFromOrder(Order order);

    OutboxEvent save(OutboxEvent outboxEvent);

    List<OutboxEvent> getTop100ByStatus(OutboxStatus outboxStatus);
}
