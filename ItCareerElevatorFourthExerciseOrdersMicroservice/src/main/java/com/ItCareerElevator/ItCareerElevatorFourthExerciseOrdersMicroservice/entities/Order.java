package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends CommonEntity {

    @ManyToOne
    private User customer;

    @OneToMany
    private Set<OrderItem> orderItems;
}
