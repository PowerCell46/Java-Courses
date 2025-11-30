package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiMeasurementUnit;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.LoiMeasurementUnitRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.LoiMeasurementUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoiMeasurementUnitServiceImpl implements LoiMeasurementUnitService {

    private final LoiMeasurementUnitRepository loiMeasurementUnitRepository;

    @Override
    public Optional<LoiMeasurementUnit> getByListOptionItemCode(Long code) {
        return loiMeasurementUnitRepository.findByListOptionItemCode(code);
    }
}
