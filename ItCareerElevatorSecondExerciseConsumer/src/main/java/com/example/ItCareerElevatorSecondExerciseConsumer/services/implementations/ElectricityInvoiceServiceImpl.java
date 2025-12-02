package com.example.ItCareerElevatorSecondExerciseConsumer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.DatabaseFileService;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.ElectricityInvoiceService;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.EmailService;
import com.example.ItCareerElevatorSecondExerciseConsumer.utils.FillHtmlTemplateData;
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

        byte[] pdfByteArray = createPdfInvoiceByteArray(electricityInvoiceDTO);

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

    private byte[] createPdfInvoiceByteArray(ElectricityInvoiceDTO electricityInvoiceDTO) {
        FillHtmlTemplateData fillHtmlTemplateData = new FillHtmlTemplateData(electricityInvoiceDTO);

        String[] fillData = fillHtmlTemplateData.toArray();

        try {
            InputStream htmlInputStream = fillHtmlTemplate(fillData, htmlTemplateFilePath);

            return convertHtmlInputStreamToPdfByteArray(htmlInputStream);

        } catch (IOException e) {
            // TODO: Throw custom exception
            throw new RuntimeException(e);
        }
    }
}
