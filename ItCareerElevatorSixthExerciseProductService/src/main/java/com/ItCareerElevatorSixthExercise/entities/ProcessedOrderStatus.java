package com.ItCareerElevatorSixthExercise.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessedOrderStatus {

    PROCESSING("PROCESSING"),

    RESERVED("RESERVED"),

    NOT_IN_STOCK("NOT_IN_STOCK"), // TODO: Write chron job until it becomes SENT_TO_KAFKA

    SENT_TO_KAFKA("SENT_TO_KAFKA"),

    RETRY_KAFKA_SEND("RETRY_KAFKA_SEND");

    private final String message;
}
