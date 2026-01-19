package com.ItCareerElevatorSixthExercise.entities.listeners;

import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.services.interfaces.SnowflakeIdGeneratorService;
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
