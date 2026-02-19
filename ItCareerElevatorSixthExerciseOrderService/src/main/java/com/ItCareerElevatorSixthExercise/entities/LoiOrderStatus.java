package com.ItCareerElevatorSixthExercise.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoiOrderStatus extends ListOptionItem {

    public static final Long NOT_IN_STOCK = 1L;

    public static final Long SYSTEM_FAILURE = 2L;

    public static final Long CREATED = 3L;

    public static final Long RESERVED = 4L;

    public static final Long CONFIRMED = 5L;

    public static final Long CANCELLED = 6L;

    public LoiOrderStatus(String name, Long code) {
        super(name, code);
    }
}
