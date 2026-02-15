package com.ItCareerElevatorSixthExercise.exceptions;

public class NoSuchLoiOrderStatusException extends RuntimeException {

    public NoSuchLoiOrderStatusException(String message) {
        super(message);
    }

    public NoSuchLoiOrderStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
