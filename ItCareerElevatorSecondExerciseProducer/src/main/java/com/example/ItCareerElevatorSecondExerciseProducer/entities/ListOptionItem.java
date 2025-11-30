package com.example.ItCareerElevatorSecondExerciseProducer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "list_option_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"dtype", "listOptionItemCode"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ListOptionItem extends CommonEntity {

    @Column
    private String listOptionItemName;

    @Column
    private Long listOptionItemCode;
}
