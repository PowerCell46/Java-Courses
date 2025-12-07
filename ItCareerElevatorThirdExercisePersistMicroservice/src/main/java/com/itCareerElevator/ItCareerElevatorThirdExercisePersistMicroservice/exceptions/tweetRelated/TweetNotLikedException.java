package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.tweetRelated;

public class TweetNotLikedException extends RuntimeException {

    public TweetNotLikedException(String message) {
        super(message);
    }

    public TweetNotLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
