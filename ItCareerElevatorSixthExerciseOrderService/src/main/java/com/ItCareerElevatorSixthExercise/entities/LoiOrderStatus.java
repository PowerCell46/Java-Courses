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

    public static final Long INTERNAL_FAILURE = 2L; // Not sent to Kafka, stuck in PROCESSING

    public static final Long CREATED = 3L;

    public static final Long RESERVED = 4L;

    public static final Long MISSING_WALLET_ADDRESS = 5L;

    public static final Long UNPAID = 6L; // Didn't pay in allowed time window

    public static final Long PAID = 7L;

    public static final Long CANCELLED = 8L;

    public static final Long INSUFFICIENT_BALANCE = 9L;

    public LoiOrderStatus(String name, Long code) {
        super(name, code);
    }
}
