package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    MISSING_WALLET_ADDRESS,

    PROCESSING,

    TIMED_OUT,

    PAID,

    SENT_TO_KAFKA,

    RETRY_KAFKA_SEND
}
