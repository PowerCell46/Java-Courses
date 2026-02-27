package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    MISSING_WALLET_ADDRESS, // 0

    INSUFFICIENT_BALANCE, // 1

    PROCESSING, // 2

    TIMED_OUT, // 3

    PAID, // 4

    SENT_TO_KAFKA // 5
}
