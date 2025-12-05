package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions;

public class InvalidSnowflakeIdException extends RuntimeException {

    public InvalidSnowflakeIdException(String message) {
        super(message);
    }

    public InvalidSnowflakeIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
