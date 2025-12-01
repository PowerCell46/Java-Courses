package com.example.ItCareerElevatorSecondExerciseConsumer.utils;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.ItCareerElevatorSecondExerciseConsumer.utils.CurrencyUtils.convertNumbersToBgnCurrency;

public class FillHtmlTemplate {

    private static final String STANDARD_EUROPEAN_DATE_FORMAT = "dd-MM-yyyy";

    private static final BigDecimal LEV_EURO_RATIO = BigDecimal.valueOf(1.95583);


    private String documentTypeName;

    private String invoiceNUmber;

    private String generationDateName;

    private String taxEventDateName;

    private String periodFromName;

    private String periodToName;

    private String measurementUnitName;

    private String totalQuantityName;

    private String priceInLevsName;

    private String priceInEurosName;

    private String totalSumInLevsName;

    private String totalSumInEurosName;

    private String vatAmountInLevsName;

    private String vatAmountInEurosName;

    private String totalAmountInLevsWithVatName;

    private String totalAmountInEurosWithVatName;

    private String paymentAmountInEurosName;

    private String paymentAmountInLevsName;


    public FillHtmlTemplate(ElectricityInvoiceDTO electricityInvoiceDTO) {
        this.documentTypeName = electricityInvoiceDTO.getLoiDocumentTypeName();
        this.invoiceNUmber = electricityInvoiceDTO.getInvoiceNumber();
        this.generationDateName = LocalDate.now().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));
        this.taxEventDateName = electricityInvoiceDTO.getTaxEventDate().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));
        this.periodFromName = electricityInvoiceDTO.getPeriodFrom().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));
        this.periodToName = electricityInvoiceDTO.getPeriodTo().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));
        this.measurementUnitName = electricityInvoiceDTO.getLoiMeasurementUnitName();

        this.totalQuantityName = electricityInvoiceDTO
                .getQuantity()
                .setScale(3, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.priceInEurosName = electricityInvoiceDTO
                .getSinglePrice()
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.priceInLevsName = electricityInvoiceDTO
                .getSinglePrice()
                .multiply(LEV_EURO_RATIO)
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.totalSumInEurosName = electricityInvoiceDTO
                .getTotalSumWithoutVAT()
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.totalSumInLevsName = electricityInvoiceDTO
                .getTotalSumWithoutVAT()
                .multiply(LEV_EURO_RATIO)
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.vatAmountInEurosName = electricityInvoiceDTO
                .getVAT()
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.vatAmountInLevsName = electricityInvoiceDTO
                .getVAT()
                .multiply(LEV_EURO_RATIO)
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');

        this.totalAmountInEurosWithVatName = calculateTotalAmount(electricityInvoiceDTO.getTotalSumWithoutVAT(), electricityInvoiceDTO.getVAT());

        this.totalAmountInLevsWithVatName = calculateTotalAmount(electricityInvoiceDTO.getTotalSumWithoutVAT().multiply(LEV_EURO_RATIO), electricityInvoiceDTO.getVAT().multiply(LEV_EURO_RATIO));

        this.paymentAmountInEurosName = convertNumbersToBgnCurrency(
                electricityInvoiceDTO.getTotalSumWithoutVAT().add(electricityInvoiceDTO.getVAT())
                        .setScale(2, RoundingMode.HALF_UP)
                        .remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue()
        );

        this.paymentAmountInLevsName = convertNumbersToBgnCurrency(
                electricityInvoiceDTO.getTotalSumWithoutVAT().multiply(LEV_EURO_RATIO).add(electricityInvoiceDTO.getVAT().multiply(LEV_EURO_RATIO))
                        .setScale(2, RoundingMode.HALF_UP)
                        .remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).intValue()
        );
    }

    private String calculateTotalAmount(BigDecimal totalSumWithoutVAT, BigDecimal VAT) {
        return totalSumWithoutVAT
                .add(VAT)
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');
    }

    public String[] getData() {
        final int numberOfCopies = 2;

        List<String> resultList = new ArrayList<>();

        for (int currentCopyNumber = 0; currentCopyNumber < numberOfCopies; ++currentCopyNumber) {
            resultList.add(String.format("%s - САМОФАКТУРИРАНЕ", this.documentTypeName));

            // Доставчик
            resultList.add(this.documentTypeName); // Тип на документа
            resultList.add(this.invoiceNUmber); // Фактура Номер
            resultList.add(this.generationDateName); // Generation Date
            resultList.add(this.taxEventDateName); // Tax Event Date
            resultList.add("Петър Герджиков"); // Legal Person Name
            resultList.add("Бул. Христо Ботев"); // Legal Person Address
            resultList.add("0338135678"); // Идентификационен № | ЕИК
            resultList.add("BG352042342"); // Идентификационен № по ДДС
            resultList.add("BGN4234234234"); // Банкова сметка

            // Получател
            resultList.add("Получател 1"); // Получател име
            resultList.add("Варна, ул. ..."); // Адрес на получателя
            resultList.add("7138110618"); // Идентификационен Номер | ЕИК
            resultList.add("BG35242342"); // Идентификационен № по ДДС

            // Таблица
            resultList.add("Произведена енергия"); // Име на кода на услугата
            resultList.add("Централа 1"); // Име на централата
            resultList.add(this.periodFromName); // Период от
            resultList.add(this.periodToName); // Период до
            resultList.add(this.measurementUnitName); // Loi Measurement Unit
            resultList.add(this.totalQuantityName); // Quantity
            resultList.add(this.priceInEurosName); // Price in Euros
            resultList.add(this.priceInLevsName); // Price in Levs
            resultList.add(this.totalSumInEurosName); // Total Sum in Euros
            resultList.add(this.totalSumInLevsName); // Total Sum in Levs

            // Totals Table
            resultList.add(this.totalSumInLevsName); // Данъчна основа в лева
            resultList.add(this.totalSumInEurosName); // Данъчна основа в евро
            resultList.add(this.vatAmountInLevsName); // ДДС в лева
            resultList.add(this.vatAmountInEurosName); // ДДС в евро
            resultList.add(this.totalAmountInLevsWithVatName); // Обща стойност в лева
            resultList.add(this.totalAmountInEurosWithVatName); // Обща стойност в евро

            // Billed Sums
            resultList.add(this.paymentAmountInLevsName); // Сума за плащане в лева
            resultList.add(this.paymentAmountInEurosName); // Сума за плащане в евро
            resultList.add(String.format("(%s)", "PowerCell46")); // Име на служителя, който натиска бутона
        }

        return resultList.toArray(new String[resultList.size()]);
    }
}
