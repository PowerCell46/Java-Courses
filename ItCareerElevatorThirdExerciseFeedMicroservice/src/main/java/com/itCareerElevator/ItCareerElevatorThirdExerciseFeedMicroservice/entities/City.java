package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "cities",
        indexes = {
                @Index(name = "idx_name_unique", columnList = "name", unique = true)
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City extends CommonEntity {

    @Column
    private String name;
}
