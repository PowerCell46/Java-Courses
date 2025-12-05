package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities;

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
        name = "countries",
        indexes = {
                @Index(name = "uk_countries_name", columnList = "name", unique = true)
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country extends CommonEntity {

    @Column
    private String name;
}
