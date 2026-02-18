package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {

    List<ProcessedOrder> findAllByStatus(
            ProcessedOrderStatus status
    );

    List<ProcessedOrder> findAllByStatusAndLastModifiedAtBefore(ProcessedOrderStatus status, LocalDateTime from);
}
