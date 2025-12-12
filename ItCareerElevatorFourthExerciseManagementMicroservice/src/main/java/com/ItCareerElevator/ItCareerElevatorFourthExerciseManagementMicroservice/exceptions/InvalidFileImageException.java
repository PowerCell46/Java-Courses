package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions;

public class InvalidFileImageException extends RuntimeException {

    public InvalidFileImageException(String message) {
        super(message);
    }

    public InvalidFileImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
