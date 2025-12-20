package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.exceptions.ErrorMailingPdfInvoiceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.exceptions.NoSuchOrderException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.repositories.OrderRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces.EmailService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces.OrderService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.fillHtml.FillHtmlInvoiceTemplateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.HtmlUtils.fillHtmlTemplate;
import static com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.PdfUtils.convertHtmlInputStreamToPdfByteArray;
import static com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.PdfUtils.savePdfToFileSystem;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${config.invoice.template}")
    private String HTML_TEMPLATE_FILE_PATH;

    private final EmailService emailService;
    private final OrderRepository orderRepository;

    @Override
    public void sendPdfInvoiceThroughEmail(String orderId) {
        log.info("Starting the generation process of the PDF.");

        Order order = getById(orderId);
        byte[] pdfByteArray = createPdfInvoiceByteArray(order);

        order.setInvoiceUrl(savePdfToFileSystem(pdfByteArray, orderId));
        save(order);

        log.info("Sending the PDF document to the customer through email.");

        emailService.sendInvoiceEmail(
                "peter.gerdzhikov.contact@gmail.com", // TODO: Recipient is hardcoded at the moment (we don't have email property in the User entity)
                "Поръчка № º " + orderId,
                """
                        Здравейте,
                            Вашата поръчка беше успешно регистрирана.
                            Към това съобщение ще намерите прикачена фактура за вашата поръчка.
                        
                            Моля, не отговаряйте на този имейл, тъй като е автоматично генериран.
                        """,
                pdfByteArray
        );
    }

    @Override
    public Order getById(String id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchOrderException(String.format("No order found with id %s.", id))
                );
    }

    @Override
    public Order save(Order order) {
        log.info("Persisting {}'s order to the database.", order.getCustomer().getUsername());

        return orderRepository.save(order);
    }

    @Override
    public byte[] createPdfInvoiceByteArray(Order order) {
        FillHtmlInvoiceTemplateData htmlInvoiceTemplateData = new FillHtmlInvoiceTemplateData(order);

        String[] fillData = htmlInvoiceTemplateData.toArray();

        try {
            InputStream htmlInputStream = fillHtmlTemplate(fillData, HTML_TEMPLATE_FILE_PATH);

            return convertHtmlInputStreamToPdfByteArray(htmlInputStream);

        } catch (IOException ex) {
            log.error("Error occurred in the generation process.", ex);

            throw new ErrorMailingPdfInvoiceException("Failed to generate PDF document.", ex);

        } catch (ArrayIndexOutOfBoundsException ex) {
            log.error("Dynamic entries and template placeholders don't match.", ex);

            throw new ErrorMailingPdfInvoiceException("Failed to generate PDF document.", ex);
        }
    }
}
