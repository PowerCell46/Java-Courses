package com.ItCareerElevatorSixthExercise.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FailureReserveItemReason {

    NOT_IN_STOCK("NOT_IN_STOCK"),

    KAFKA_ERROR("KAFKA_ERROR");

    private final String message;
}
