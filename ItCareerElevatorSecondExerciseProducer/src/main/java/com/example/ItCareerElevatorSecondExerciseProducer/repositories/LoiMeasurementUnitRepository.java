package com.example.ItCareerElevatorSecondExerciseProducer.repositories;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiMeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoiMeasurementUnitRepository extends JpaRepository<LoiMeasurementUnit, Long> {

    Optional<LoiMeasurementUnit> findByListOptionItemCode(Long code);
}
