package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username_unique", columnList = "username", unique = true)
        }
)
@Getter
@Setter
public class User extends CommonEntity {

    @Column
    private String username;

    @Column
    private String password;
}
