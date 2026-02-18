package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "processed_orders")
public class ProcessedOrder {

    @Id
    private Long orderId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private ProcessedOrderStatus status;

    public ProcessedOrder(Long orderId, ProcessedOrderStatus status) {
        this.orderId = orderId;
        this.userId = "N/A";
        this.totalPrice = BigDecimal.ZERO;
        this.status = status;
    }
}
