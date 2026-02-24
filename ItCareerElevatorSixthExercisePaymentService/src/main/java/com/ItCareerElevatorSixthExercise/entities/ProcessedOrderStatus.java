package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    MISSING_WALLET_ADDRESS, // 0

    PROCESSING, // 1

    TIMED_OUT, // 2

    PAID, // 3

    SENT_TO_KAFKA, // 4

    RETRY_KAFKA_SEND // 5 // TODO: You don't need this, you can retry PAID with 5 minutes without altering
}
