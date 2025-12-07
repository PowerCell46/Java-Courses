package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated;

public class UserNotFollowingException extends RuntimeException {

    public UserNotFollowingException(String message) {
        super(message);
    }

    public UserNotFollowingException(String message, Throwable cause) {
        super(message, cause);
    }
}
