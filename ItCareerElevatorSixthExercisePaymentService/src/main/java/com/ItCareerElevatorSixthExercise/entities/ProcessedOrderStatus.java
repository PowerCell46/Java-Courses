package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    MISSING_WALLET_ADDRESS,

    PROCESSING,

    TIMED_OUT,

    PAID,

    SENT_TO_KAFKA,

    RETRY_KAFKA_SEND // TODO: You don't need this, you can retry PAID with 5 minutes without altering
}
