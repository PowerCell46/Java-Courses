package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfUtils {

    public static byte[] convertHtmlInputStreamToPdfByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(inputStream, outputStream);

            return outputStream.toByteArray();
        }
    }
}
