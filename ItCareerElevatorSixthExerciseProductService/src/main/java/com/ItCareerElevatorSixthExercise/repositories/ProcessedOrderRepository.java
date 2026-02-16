package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {
}
