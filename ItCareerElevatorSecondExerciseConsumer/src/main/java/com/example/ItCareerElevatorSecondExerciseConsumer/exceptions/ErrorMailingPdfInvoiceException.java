package com.example.ItCareerElevatorSecondExerciseConsumer.exceptions;

public class ErrorMailingPdfInvoiceException extends RuntimeException {

    public ErrorMailingPdfInvoiceException(String message) {
        super(message);
    }

    public ErrorMailingPdfInvoiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
