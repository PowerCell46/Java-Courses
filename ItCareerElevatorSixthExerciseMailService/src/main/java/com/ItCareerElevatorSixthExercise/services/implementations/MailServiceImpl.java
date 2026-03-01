package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.UserRegisteredDTO;
import com.ItCareerElevatorSixthExercise.exceptions.MailTemplateException;
import com.ItCareerElevatorSixthExercise.exceptions.MailingProcessException;
import com.ItCareerElevatorSixthExercise.services.interfaces.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendRegistrationMail(UserRegisteredDTO registerDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(registerDTO.getEmail());
            helper.setSubject("Welcome to Web Store!");

            helper.setText(formatMessageBody(registerDTO));
            mailSender.send(message);

        } catch (MessagingException ex) {
            log.warn("Exception occurred while constructing/send the email.", ex);
            throw new MailingProcessException("Failed to send message email.", ex);
        }
    }

    private String formatMessageBody(UserRegisteredDTO registeredDTO) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/registerUserTemplate.html");
            final String htmlTemplateContent = new String(
                    resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            return htmlTemplateContent
                    .replace("{{username}}", registeredDTO.getUsername())
                    .replace("{{username}}", registeredDTO.getUsername())
                    .replace("{{email}}", registeredDTO.getEmail());

        } catch (IOException ex) {
            log.warn("Error reading the registerUserTemplate.", ex);
            throw new MailTemplateException(ex);
        }
    }
}
