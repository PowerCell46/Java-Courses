package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    PROCESSING, // 0

    MISSING_WALLET_ADDRESS, // 1

    TIMED_OUT, // 2

    INSUFFICIENT_BALANCE, // 3

    PAID, // 4

    SENT_TO_KAFKA // 5
}
