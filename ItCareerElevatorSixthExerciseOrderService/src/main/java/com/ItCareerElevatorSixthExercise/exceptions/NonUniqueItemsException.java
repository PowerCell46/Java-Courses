package com.ItCareerElevatorSixthExercise.exceptions;

public class NonUniqueItemsException extends RuntimeException {

    public NonUniqueItemsException(String message) {
        super(message);
    }

    public NonUniqueItemsException(String message, Throwable cause) {
        super(message, cause);
    }
}
