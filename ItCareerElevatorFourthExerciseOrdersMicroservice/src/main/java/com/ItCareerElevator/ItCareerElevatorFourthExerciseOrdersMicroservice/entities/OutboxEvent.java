package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.OutboxStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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

    @Column(nullable = false, length = 50)
    private String entityType;

    @Column(nullable = false, length = 64)
    private String entityId;

    @Column(nullable = false, length = 100)
    private String eventType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OutboxStatusEnum status;

    @Column(nullable = false)
    private Integer retryCount;

    @Column(length = 2000)
    private String error; // * ;error1;error2;...

    @Column
    private LocalDateTime processedAt;

    public OutboxEvent(String entityType, String entityId, String eventType, OutboxStatusEnum status) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.eventType = eventType;
        this.status = status;
        this.retryCount = 0;
    }
}
