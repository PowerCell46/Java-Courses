package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.exceptions;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
