package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.LastProcessedBlockNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastProcessedBlockNumberRepository extends JpaRepository<LastProcessedBlockNumber, Long> {
}
