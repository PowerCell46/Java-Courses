package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEvent extends CommonEntity {

    private String entityType;

    private String entityId;

    private String eventType;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private Integer retryCount;

    private String error;

    private LocalDateTime processedAt;

    public OutboxEvent(String entityType, String entityId, String eventType, OutboxStatus status) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.eventType = eventType;
        this.status = status;
        this.retryCount = 0;
    }
}
// TODO: add Col annotations