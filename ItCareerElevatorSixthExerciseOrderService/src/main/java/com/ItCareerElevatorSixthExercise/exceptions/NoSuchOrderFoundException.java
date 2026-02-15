package com.ItCareerElevatorSixthExercise.exceptions;

public class NoSuchOrderFoundException extends RuntimeException {

    public NoSuchOrderFoundException(String message) {
        super(message);
    }

    public NoSuchOrderFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
