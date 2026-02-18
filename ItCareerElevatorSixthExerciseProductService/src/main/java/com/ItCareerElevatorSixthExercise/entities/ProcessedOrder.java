package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false)
    private String status;

    public ProcessedOrder(Long orderId, String status) {
        this.orderId = orderId;
        this.userId = "N/A";
        this.totalPrice = BigDecimal.ZERO;
        this.status = status;
    }
}
