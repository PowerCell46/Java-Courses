package com.example.ItCareerElevatorSecondExerciseProducer.exceptions;

public class InvalidRelationshipException extends RuntimeException {

    public InvalidRelationshipException(String message) {
        super(message);
    }

    public InvalidRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
}
