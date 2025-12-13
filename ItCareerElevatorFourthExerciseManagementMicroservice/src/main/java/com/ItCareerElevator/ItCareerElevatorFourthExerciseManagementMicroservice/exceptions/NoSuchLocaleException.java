package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions;

public class NoSuchLocaleException extends RuntimeException {

    public NoSuchLocaleException(String message) {
        super(message);
    }

    public NoSuchLocaleException(String message, Throwable cause) {
        super(message, cause);
    }
}
