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
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Check(constraints = "balance >= 0")
public class User {

    @Id
    private String id; // UUID

    @Column
    private BigDecimal balance;

    @Column
    private String walletAddress;

    @Version
    private Long version;

    public User(String walletAddress, String id) {
        this.id = id;
        this.walletAddress = walletAddress;
    }

    public User(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }
}
