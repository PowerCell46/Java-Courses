package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "list_option_items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ListOptionItem extends CommonEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long code;
}
