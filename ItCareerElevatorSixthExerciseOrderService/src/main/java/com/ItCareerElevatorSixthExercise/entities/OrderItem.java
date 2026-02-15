package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends CommonEntity {

    private String productId;

    private Integer quantity;
}
