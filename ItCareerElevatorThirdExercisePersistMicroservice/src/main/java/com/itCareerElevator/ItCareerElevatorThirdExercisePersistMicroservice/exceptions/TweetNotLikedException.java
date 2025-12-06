package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions;

public class TweetNotLikedException extends RuntimeException {

    public TweetNotLikedException(String message) {
        super(message);
    }

    public TweetNotLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
