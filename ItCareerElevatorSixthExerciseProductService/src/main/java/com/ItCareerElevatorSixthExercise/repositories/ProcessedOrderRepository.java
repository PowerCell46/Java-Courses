package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessedOrderRepository extends JpaRepository<ProcessedOrder, Long> {

    List<ProcessedOrder> findAllByIsSentToKafkaAndRetryTimesGreaterThanEqualAndRetryTimesLessThanEqual(
            Boolean isSentToKafka,
            Integer minRetryTimes,
            Integer maxRetryTimes
    );
}
