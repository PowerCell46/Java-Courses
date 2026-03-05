package com.ItCareerElevatorSixthExercise.services.interfaces.reservation;

import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;

public interface ProcessedOrderPersistenceService {

    ProcessedOrder save(ProcessedOrder processedOrder);

    void deleteById(Long id);
}
