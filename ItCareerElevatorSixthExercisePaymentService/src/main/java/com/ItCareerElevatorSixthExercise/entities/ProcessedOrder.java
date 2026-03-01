package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "processed_orders")
public class ProcessedOrder {

    @Id
    private Long orderId; // snowflakeId

    @Column(nullable = false)
    private String userId; // UUID

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(value = EnumType.ORDINAL)
    private ProcessedOrderStatus status;

    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;

    @PrePersist // * Called once before the entity is first saved (INSERT)
    public void prePersist() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate // * Called every time the entity is updated (UPDATE)
    public void preUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    public ProcessedOrder(Long orderId, String userId, BigDecimal totalPrice, ProcessedOrderStatus status) {
        this.orderId = orderId;
        this.lastModifiedAt = LocalDateTime.now();
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
