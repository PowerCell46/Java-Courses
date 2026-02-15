package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends CommonEntity {

    private String userId; // UUID

    @ManyToOne
    private LoiOrderStatus orderStatus;

    @OneToMany
    private List<OrderItem> items;

    public Order(String userId, LoiOrderStatus orderStatus) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.items = new ArrayList<>();
    }
}
