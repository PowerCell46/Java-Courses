package com.example.ItCareerElevatorSecondExerciseConsumer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseConsumer.exceptions.ErrorMailingPdfInvoiceException;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.DatabaseFileService;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.ElectricityInvoiceService;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.EmailService;
import com.example.ItCareerElevatorSecondExerciseConsumer.utils.FillHtmlInvoiceTemplateData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static com.example.ItCareerElevatorSecondExerciseConsumer.utils.HtmlUtils.fillHtmlTemplate;
import static com.example.ItCareerElevatorSecondExerciseConsumer.utils.PdfUtils.convertHtmlInputStreamToPdfByteArray;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElectricityInvoiceServiceImpl implements ElectricityInvoiceService {

    @Value("${config.invoice.template}")
    private String htmlTemplateFilePath;

    private final DatabaseFileService databaseService;
    private final EmailService emailService;

    @Override
    public void sendPdfInvoiceThroughEmail(ElectricityInvoiceDTO electricityInvoiceDTO) {
        log.info("Starting the generation process of the PDF.");

        byte[] pdfByteArray = createPdfInvoiceByteArrayForElectricityInvoice(electricityInvoiceDTO);

        databaseService.savePdf(electricityInvoiceDTO.getSnowflakeId(), pdfByteArray);

        log.info("Sending the PDF document to the User through email.");

        emailService.sendInvoiceEmail(
                electricityInvoiceDTO.getRecipientEmail(),
                "Electricity invoice #" + electricityInvoiceDTO.getInvoiceNumber(),
                """
                        Dear customer,

                        Your electricity invoice #%s is attached as a PDF document.
                        Please review it at your convenience. If you have any questions,
                        reply to this email.

                        Best regards,
                        Your Electricity Provider
                        """.formatted(electricityInvoiceDTO.getInvoiceNumber()),
                pdfByteArray
        );
    }

    private byte[] createPdfInvoiceByteArrayForElectricityInvoice(ElectricityInvoiceDTO electricityInvoiceDTO) {
        FillHtmlInvoiceTemplateData fillHtmlInvoiceTemplateData = new FillHtmlInvoiceTemplateData(electricityInvoiceDTO);

        String[] fillData = fillHtmlInvoiceTemplateData.toArray();

        try {
            InputStream htmlInputStream = fillHtmlTemplate(fillData, htmlTemplateFilePath);

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
