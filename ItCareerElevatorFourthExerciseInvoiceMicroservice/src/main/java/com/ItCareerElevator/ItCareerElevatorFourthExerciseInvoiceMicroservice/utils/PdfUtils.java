package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.exceptions.ErrorMailingPdfInvoiceException;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class PdfUtils {

    public static byte[] convertHtmlInputStreamToPdfByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(inputStream, outputStream);

            return outputStream.toByteArray();
        }
    }

    public static String savePdfToFileSystem(byte[] pdfByteArray, String orderId) {
        final Path directoryPath = Paths
                .get("invoices")
                .toAbsolutePath()
                .normalize();

        String fileName = String.format("invoice_%s.pdf", orderId);

        try {
            Files.createDirectories(directoryPath);

            Path filePath = directoryPath.resolve(fileName);

            Files.write(filePath, pdfByteArray);

            return filePath.toString();

        } catch (IOException ex) {
            log.error("Error during the persisting of the invoice to the file system.");

            throw new ErrorMailingPdfInvoiceException("Failed to generate PDF document.", ex);
        }
    }
}
