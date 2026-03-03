package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends CommonEntity {

    @Column(nullable = false)
    private String userId; // UUID

    @Column(nullable = false)
    private String userEmail;

    @Column
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "loi_order_status_id")
    private LoiOrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> items;

    public Order(String userId, String userEmail, LoiOrderStatus orderStatus) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.totalPrice = null;
        this.orderStatus = orderStatus;
        this.items = new ArrayList<>();
    }
}
