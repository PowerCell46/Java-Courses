package com.ItCareerElevatorSixthExercise.entities.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "locales")
public class Locale {

    public static final String DEFAULT_LANGUAGE_CODE = "en";

    public static final String DEFAULT_LANGUAGE_NAME = "English";

    @Id
    @Column(length = 10)
    private String code;

    @Column(nullable = false)
    private String name;
}
