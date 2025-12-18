package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "locales",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_code_is_deleted",
                        columnNames = {"code", "is_deleted"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Locale extends CommonEntity {

    @Column(nullable = false, length = 10)
    private String code;
}
