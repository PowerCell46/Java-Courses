package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "producers")
@Getter
@Setter
@SQLDelete(sql = "UPDATE producers SET is_deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
public class Producer extends CommonEntity {

    @Column(nullable = false)
    private String name;
}
