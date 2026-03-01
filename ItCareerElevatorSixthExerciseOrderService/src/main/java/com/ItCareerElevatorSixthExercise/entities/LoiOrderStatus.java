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

    public static final Long CREATED = 1L;

    public static final Long INTERNAL_FAILURE = 2L; // Stuck in PROCESSING | Kafka failure

    public static final Long INVALID_PRODUCTS = 3L; // Invalid productId used in the order

    public static final Long NOT_IN_STOCK = 4L;

    public static final Long RESERVED = 5L;

    public static final Long MISSING_WALLET_ADDRESS = 6L;

    public static final Long UNPAID = 7L; // TIMED_OUT (Didn't pay in allowed time window)

    public static final Long INSUFFICIENT_BALANCE = 8L;

    public static final Long PAID = 9L;

    // public static final Long CANCELLED = 10L;

    public LoiOrderStatus(String name, Long code) {
        super(name, code);
    }
}
