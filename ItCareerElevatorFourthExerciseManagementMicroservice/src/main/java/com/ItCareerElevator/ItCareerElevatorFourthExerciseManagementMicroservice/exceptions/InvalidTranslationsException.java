package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions;

public class InvalidTranslationsException extends RuntimeException {

    public InvalidTranslationsException(String message) {
        super(message);
    }

    public InvalidTranslationsException(String message, Throwable cause) {
        super(message, cause);
    }
}
