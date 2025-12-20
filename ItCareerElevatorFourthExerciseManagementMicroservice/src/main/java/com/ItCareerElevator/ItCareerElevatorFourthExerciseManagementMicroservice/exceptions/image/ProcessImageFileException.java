package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.image;

public class ProcessImageFileException extends RuntimeException {

    public ProcessImageFileException(String message) {
        super(message);
    }

    public ProcessImageFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
