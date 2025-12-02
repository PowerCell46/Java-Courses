package com.example.ItCareerElevatorSecondExerciseConsumer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

            final String FILE_NAME = "electricity-invoice.pdf";
            final String CONTENT_TYPE = "application/pdf";

            ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
            helper.addAttachment(FILE_NAME, pdfResource, CONTENT_TYPE);

            mailSender.send(message);

        } catch (MessagingException ex) {
            log.warn("Messaging exception occurred.");

            throw new RuntimeException("Failed to send invoice email", ex); // TODO: There has to be a better way
        }
    }
}
