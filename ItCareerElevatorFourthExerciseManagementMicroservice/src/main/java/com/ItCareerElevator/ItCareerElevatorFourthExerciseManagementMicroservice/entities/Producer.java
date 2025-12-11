package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producer extends CommonEntity {

    @Column
    private String name; // gymBeam, feral,
}
