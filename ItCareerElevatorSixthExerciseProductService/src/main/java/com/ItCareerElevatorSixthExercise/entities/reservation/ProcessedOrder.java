package com.ItCareerElevatorSixthExercise.entities.reservation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime lastModifiedAt;

    @Column(nullable = false)
    private String userId; // UUID

    @Column(nullable = false)
    private String userEmail;

    @Column
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ProcessedOrderStatus status;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "processedOrder")
    private List<ReservedProduct> reservedProducts;

    @PrePersist // * Called once before the entity is first saved (INSERT)
    public void prePersist() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate // * Called every time the entity is updated (UPDATE)
    public void preUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    public ProcessedOrder(Long orderId, String userId, String userEmail, ProcessedOrderStatus status) {
        this.orderId = orderId;
        this.lastModifiedAt = LocalDateTime.now();
        this.userId = userId;
        this.userEmail = userEmail;
        this.status = status;
        this.reservedProducts = new ArrayList<>();
    }
}
