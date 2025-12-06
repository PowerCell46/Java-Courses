package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions;

public class NoSuchTweetException extends RuntimeException {

    public NoSuchTweetException(String message) {
        super(message);
    }

    public NoSuchTweetException(String message, Throwable cause) {
        super(message, cause);
    }
}
