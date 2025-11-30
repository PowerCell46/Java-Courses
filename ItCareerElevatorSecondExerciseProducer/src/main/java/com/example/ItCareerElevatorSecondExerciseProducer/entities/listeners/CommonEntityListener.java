package com.example.ItCareerElevatorSecondExerciseProducer.entities.listeners;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.CommonEntity;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.SnowflakeIdGeneratorService;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonEntityListener {

    private final SnowflakeIdGeneratorService idGenerator;

    @PrePersist
    public void prePersist(CommonEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.nextId());
        }
    }
}
