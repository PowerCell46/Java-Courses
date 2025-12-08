package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.listeners;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.SnowflakeIdGeneratorService;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommonEntityListener {

    private final SnowflakeIdGeneratorService idGenerator;

    @PrePersist
    public void prePersist(CommonEntity entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.nextId());
        }
        entity.setLastModifiedAt(LocalDateTime.now());
    }
}
