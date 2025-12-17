package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities;

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
        name = "producers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_producer_name_is_deleted",
                        columnNames = {"name", "is_deleted"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producer extends CommonEntity {

    @Column(nullable = false)
    private String name;
}
