package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.OutboxEvent;

public interface OutboxEventService {

    OutboxEvent constructNonPersistedOutboxEventFromOrder(Order order);

    OutboxEvent save(OutboxEvent outboxEvent);
}
