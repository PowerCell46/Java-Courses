package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities;

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
    name = "locales",
    indexes = {
        @Index(name = "idx_locale_code", columnList = "code", unique = true)
    }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE locales SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
public class Locale extends CommonEntity {

    @Column(nullable = false, unique = true, length = 10)
    private String code;
}
