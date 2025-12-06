package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions;

public class TweetAlreadyLikedException extends RuntimeException {

    public TweetAlreadyLikedException(String message) {
        super(message);
    }

    public TweetAlreadyLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
