package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.listeners;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.SnowflakeIdGeneratorService;
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
