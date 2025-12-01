package com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces;

public interface EmailService {

    void sendInvoiceEmail(String recipient, String subject, String body, byte[] pdfBytes);
}
