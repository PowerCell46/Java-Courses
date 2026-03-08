package com.ItCareerElevatorSixthExercise.exceptions.product;

public class MissingDefaultTranslationException extends RuntimeException {

    public MissingDefaultTranslationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingDefaultTranslationException(String message) {
        super(message);
    }
}
