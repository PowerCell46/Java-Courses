package com.ItCareerElevatorSixthExercise.exceptions.product;

public class InvalidTranslationsException extends RuntimeException {

    public InvalidTranslationsException(String message) {
        super(message);
    }

    public InvalidTranslationsException(String message, Throwable cause) {
        super(message, cause);
    }
}
