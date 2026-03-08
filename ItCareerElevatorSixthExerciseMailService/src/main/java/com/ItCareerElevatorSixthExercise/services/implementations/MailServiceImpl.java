package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.orderCompleted.OrderCompletedDTO;
import com.ItCareerElevatorSixthExercise.DTOs.userRegistered.UserRegisteredDTO;
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
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

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

            helper.setText(formatRegistrationMessageBody(registerDTO), true);
            mailSender.send(message);

        } catch (MessagingException ex) {
            log.warn("Exception occurred while constructing/sending the email.", ex);
            throw new MailingProcessException("Failed to send email message.", ex);
        }
    }

    private String formatRegistrationMessageBody(UserRegisteredDTO registeredDTO) {
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
            log.warn("Error reading the registerUserTemplate.html", ex);
            throw new MailTemplateException(ex);
        }
    }

    @Override
    public void sendOrderMail(OrderCompletedDTO orderDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(orderDTO.getUserEmail());
            helper.setSubject("Web Store successful order!");

            helper.setText(formatOrderMessageBody(orderDTO), true);
            mailSender.send(message);

        } catch (MessagingException ex) {
            log.warn("Exception occurred while constructing/sending the email.", ex);
            throw new MailingProcessException("Failing to send email message.", ex);
        }
    }

    private String formatOrderMessageBody(OrderCompletedDTO orderDTO) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/orderCompletedTemplate.html");
            final String htmlTemplateContent = new String(
                    resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            final String productTableRow = """
                    <tr>
                        <td style="padding: 14px 24px; border-bottom: 1px solid #1e2f3f;">
                            <table role="presentation" cellspacing="0" cellpadding="0" border="0" width="100%">
                                <tr>
                                    <td width="20%"
                                        style="font-size: 14px; color: #c9d6e3; font-family: 'Courier New', Courier, monospace;">
                                        {{itemId}}
                                    </td>
                                    <td width="30%" align="center"
                                        style="font-size: 14px; color: #e8edf2; font-weight: 600;">
                                        {{itemName}}
                                    </td>
                                    <td width="20%" align="center"
                                        style="font-size: 14px; color: #c9d6e3; font-weight: 500;">
                                        {{itemQuantity}}
                                    </td>
                                    <td width="30%" align="right"
                                        style="font-size: 14px; color: #e8edf2; font-weight: 600;">
                                        {{itemPrice}} EUR
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    """;

            String productItems = orderDTO
                    .getOrderItems()
                    .stream()
                    .map(orderItemCompletedDTO ->
                            productTableRow
                                    .replace("{{itemId}}", orderItemCompletedDTO.getId())
                                    .replace("{{itemQuantity}}", orderItemCompletedDTO.getQuantity().toString())
                                    .replace("{{itemPrice}}", orderItemCompletedDTO.getPrice().setScale(2, RoundingMode.HALF_UP).toPlainString()))
                    .collect(Collectors.joining(System.lineSeparator()));

            return htmlTemplateContent
                    .replace("{{orderId}}", orderDTO.getId())
                    .replace("{{productsTableRows}}", productItems)
                    .replace("{{totalPrice}}", orderDTO.getTotalPrice().setScale(2, RoundingMode.HALF_UP).toPlainString());

        } catch (IOException ex) {
            log.warn("Error reading the orderCompletedTemplate.html", ex);
            throw new MailTemplateException(ex);
        }
    }
}
