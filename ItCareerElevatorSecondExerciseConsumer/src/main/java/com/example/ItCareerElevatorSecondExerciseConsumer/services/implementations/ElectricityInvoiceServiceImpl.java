package com.example.ItCareerElevatorSecondExerciseConsumer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces.ElectricityInvoiceService;
import com.example.ItCareerElevatorSecondExerciseConsumer.utils.FillHtmlTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static com.example.ItCareerElevatorSecondExerciseConsumer.utils.HtmlUtils.fillHtmlTemplate;
import static com.example.ItCareerElevatorSecondExerciseConsumer.utils.PdfUtils.convertHtmlInputStreamToPdfByteArray;

@Service
public class ElectricityInvoiceServiceImpl implements ElectricityInvoiceService {

    @Override
    public void sendInvoice(ElectricityInvoiceDTO electricityInvoiceDTO) {
        byte[] pdfByteArray = createPdfInvoiceByteArray(electricityInvoiceDTO);

        // TODO: Send logic...
    }

    private byte[] createPdfInvoiceByteArray(ElectricityInvoiceDTO electricityInvoiceDTO) {
        FillHtmlTemplate fillHtmlTemplate = new FillHtmlTemplate(electricityInvoiceDTO);

        String[] fillData = fillHtmlTemplate.getData();

        try {
            InputStream htmlInputStream = fillHtmlTemplate(fillData);

            return convertHtmlInputStreamToPdfByteArray(htmlInputStream);

        } catch (IOException e) {
            // TODO: Throw custom exception
            throw new RuntimeException(e);
        }
    }
}
