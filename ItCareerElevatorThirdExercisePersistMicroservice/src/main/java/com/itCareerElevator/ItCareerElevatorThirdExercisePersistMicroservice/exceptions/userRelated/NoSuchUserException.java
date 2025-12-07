package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated;

public class NoSuchUserException extends RuntimeException {

    public NoSuchUserException(String message) {
        super(message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
