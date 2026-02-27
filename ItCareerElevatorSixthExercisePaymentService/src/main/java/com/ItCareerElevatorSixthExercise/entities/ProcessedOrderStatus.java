package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    MISSING_WALLET_ADDRESS, // 0

    INSUFFICIENT_BALANCE, // 1

    PROCESSING, // 2

    TIMED_OUT, // 3

    PAID, // 4

    SENT_TO_KAFKA, // 5

    RETRY_KAFKA_SEND // 6 // TODO: You don't need this, you can retry PAID with 5 minutes without altering
}
