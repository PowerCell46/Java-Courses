package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
@Table(name = "users")
public class User {

    @Id
    private String id; // UUID

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String walletAddress;

    @Version
    private Long version;

    public User(String walletAddress, String id) {
        this.walletAddress = walletAddress;
        this.id = id;
    }
}
