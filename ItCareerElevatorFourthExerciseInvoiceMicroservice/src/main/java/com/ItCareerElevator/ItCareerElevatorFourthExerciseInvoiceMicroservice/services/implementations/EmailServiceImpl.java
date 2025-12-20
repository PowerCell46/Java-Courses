package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.exceptions.ErrorMailingPdfInvoiceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
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

            final String ATTACHED_INVOICE_FILE_NAME = "order-invoice.pdf";
            final String CONTENT_TYPE = "application/pdf";

            ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
            helper.addAttachment(ATTACHED_INVOICE_FILE_NAME, pdfResource, CONTENT_TYPE);

            mailSender.send(message);

        } catch (MessagingException ex) {
            log.warn("Exception occurred while constructing/sending the email.", ex);

            throw new ErrorMailingPdfInvoiceException("Failed to send invoice email.", ex);
        }
    }
}
