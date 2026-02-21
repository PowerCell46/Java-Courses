package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {

    List<ProcessedOrder> findAllByStatusAndLastModifiedAtBefore(ProcessedOrderStatus status, LocalDateTime before);
}
