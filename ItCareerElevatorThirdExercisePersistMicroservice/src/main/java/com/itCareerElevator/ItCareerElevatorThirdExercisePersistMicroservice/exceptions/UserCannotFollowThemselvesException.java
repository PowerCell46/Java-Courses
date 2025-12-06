package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions;

public class UserCannotFollowThemselvesException extends RuntimeException {

    public UserCannotFollowThemselvesException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCannotFollowThemselvesException(String message) {
        super(message);
    }
}
