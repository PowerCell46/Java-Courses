package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    PROCESSING, // 0

    RESERVED, // 1

    NOT_IN_STOCK, // 2

    SENT_TO_KAFKA, // 3

    RETRY_KAFKA_SEND // 4
}
