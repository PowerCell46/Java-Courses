package com.ItCareerElevatorSixthExercise.entities;

public enum ProcessedOrderStatus {

    PROCESSING,

    RESERVED,

    NOT_IN_STOCK,

    SENT_TO_KAFKA,

    RETRY_KAFKA_SEND
}
