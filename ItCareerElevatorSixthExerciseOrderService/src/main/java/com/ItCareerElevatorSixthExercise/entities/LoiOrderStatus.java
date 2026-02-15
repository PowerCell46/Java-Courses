package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
// TODO: Are there missing annotations?
public class LoiOrderStatus extends ListOptionItem {

    public static final Long CREATED = 1L;

    public static final Long CONFIRMED = 2L;

    public static final Long CANCELLED = 3L;

    public LoiOrderStatus(String name, Long code) {
        super(name, code);
    }
}
