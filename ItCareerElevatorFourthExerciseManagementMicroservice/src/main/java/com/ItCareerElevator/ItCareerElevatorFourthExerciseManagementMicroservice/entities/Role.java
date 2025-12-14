package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(
        name = "roles",
        indexes = {
                @Index(name = "idx_name_unique", columnList = "name", unique = true)
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE roles SET is_deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends CommonEntity {

    @Column(nullable = false)
    private String name;
}
