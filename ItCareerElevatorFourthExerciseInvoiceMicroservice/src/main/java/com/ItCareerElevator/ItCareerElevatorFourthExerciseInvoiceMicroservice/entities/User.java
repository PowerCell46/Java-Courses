package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_username_is_deleted",
                        columnNames = {"username", "is_deleted"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends CommonEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    public User(String username, String password) {
        super();

        this.username = username;
        this.password = password;
        this.orders = new HashSet<>();
    }
}
