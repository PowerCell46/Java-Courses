package com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiDocumentType;

import java.util.Optional;

public interface LoiDocumentTypeService {

    Optional<LoiDocumentType> getByListOptionItemCode(Long code);
}
