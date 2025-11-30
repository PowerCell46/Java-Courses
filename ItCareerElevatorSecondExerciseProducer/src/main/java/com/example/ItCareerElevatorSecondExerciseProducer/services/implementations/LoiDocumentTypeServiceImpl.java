package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiDocumentType;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.LoiDocumentTypeRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.LoiDocumentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoiDocumentTypeServiceImpl implements LoiDocumentTypeService {

    private final LoiDocumentTypeRepository loiDocumentTypeRepository;

    @Override
    public Optional<LoiDocumentType> getByListOptionItemCode(Long code) {
        return loiDocumentTypeRepository.findByListOptionItemCode(code);
    }
}
