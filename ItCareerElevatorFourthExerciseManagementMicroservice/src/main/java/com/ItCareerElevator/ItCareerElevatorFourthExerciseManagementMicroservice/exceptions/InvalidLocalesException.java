package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions;

public class InvalidLocalesException extends RuntimeException {

    public InvalidLocalesException(String message) {
        super(message);
    }

    public InvalidLocalesException(String message, Throwable cause) {
        super(message, cause);
    }
}
