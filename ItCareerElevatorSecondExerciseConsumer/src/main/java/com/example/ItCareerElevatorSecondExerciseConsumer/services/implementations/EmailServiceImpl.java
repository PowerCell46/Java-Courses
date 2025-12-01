package com.example.ItCareerElevatorSecondExerciseConsumer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendInvoiceEmail(String recipient, String subject, String body, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, false);

            ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
            helper.addAttachment("electricity-invoice.pdf", pdfResource, "application/pdf");

            mailSender.send(message);

        } catch (MessagingException ex) {
            // TODO: custom exception / logging
            throw new RuntimeException("Failed to send invoice email", ex);
        }
    }
}
