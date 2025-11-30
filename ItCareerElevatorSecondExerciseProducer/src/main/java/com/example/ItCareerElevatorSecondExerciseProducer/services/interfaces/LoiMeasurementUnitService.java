package com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiMeasurementUnit;

import java.util.Optional;

public interface LoiMeasurementUnitService {

    Optional<LoiMeasurementUnit> getByListOptionItemCode(Long code);
}
