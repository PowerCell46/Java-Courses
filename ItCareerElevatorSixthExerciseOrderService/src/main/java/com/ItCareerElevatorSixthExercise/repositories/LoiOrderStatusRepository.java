package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoiOrderStatusRepository extends JpaRepository<LoiOrderStatus, Long> {
}
