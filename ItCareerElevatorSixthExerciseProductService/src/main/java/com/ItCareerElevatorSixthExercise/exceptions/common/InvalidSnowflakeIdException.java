package com.ItCareerElevatorSixthExercise.exceptions.common;

public class InvalidSnowflakeIdException extends RuntimeException {

    public InvalidSnowflakeIdException(String message) {
        super(message);
    }

    public InvalidSnowflakeIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
