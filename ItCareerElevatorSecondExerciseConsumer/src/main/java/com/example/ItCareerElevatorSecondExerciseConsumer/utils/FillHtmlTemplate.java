package com.example.ItCareerElevatorSecondExerciseConsumer.utils;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.ItCareerElevatorSecondExerciseConsumer.utils.CurrencyUtils.convertNumberToBulgarianWords;

public class FillHtmlTemplate {

    private static final String STANDARD_EUROPEAN_DATE_FORMAT = "dd-MM-yyyy";

    private static final BigDecimal LEV_EURO_RATIO = BigDecimal.valueOf(1.95583);

    private final String documentTypeName;

    private final String invoiceNUmber;

    private final String generationDateName;

    private final String taxEventDateName;

    private final String periodFromName;

    private final String periodToName;

    private final String measurementUnitName;

    private final String totalQuantityName;

    private final String priceInLevsName;

    private final String priceInEurosName;

    private final String totalSumInLevsName;

    private final String totalSumInEurosName;

    private final String vatAmountInLevsName;

    private final String vatAmountInEurosName;

    private final String totalAmountInLevsWithVatName;

    private final String totalAmountInEurosWithVatName;

    private final String paymentAmountInEurosName;

    private final String paymentAmountInLevsName;

    public FillHtmlTemplate(ElectricityInvoiceDTO electricityInvoiceDTO) {
        this.documentTypeName = electricityInvoiceDTO.getLoiDocumentTypeName();

        this.invoiceNUmber = electricityInvoiceDTO.getInvoiceNumber();

        this.generationDateName = LocalDate.now().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));

        this.taxEventDateName = electricityInvoiceDTO.getTaxEventDate().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));

        this.periodFromName = electricityInvoiceDTO.getPeriodFrom().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));

        this.periodToName = electricityInvoiceDTO.getPeriodTo().format(DateTimeFormatter.ofPattern(STANDARD_EUROPEAN_DATE_FORMAT));

        this.measurementUnitName = electricityInvoiceDTO.getLoiMeasurementUnitName();

        this.totalQuantityName = formatBigDecimalWithScale(electricityInvoiceDTO.getQuantity(), 3);

        this.priceInEurosName = formatBigDecimalWithScale(electricityInvoiceDTO.getSinglePrice(), 2);

        this.priceInLevsName = formatBigDecimalWithScale(convertEuroToLev(electricityInvoiceDTO.getSinglePrice()), 2);

        this.totalSumInEurosName = formatBigDecimalWithScale(electricityInvoiceDTO.getTotalSumWithoutVAT(), 2);

        this.totalSumInLevsName = formatBigDecimalWithScale(convertEuroToLev(electricityInvoiceDTO.getTotalSumWithoutVAT()), 2);

        this.vatAmountInEurosName = formatBigDecimalWithScale(electricityInvoiceDTO.getVAT(), 2);

        this.vatAmountInLevsName = formatBigDecimalWithScale(convertEuroToLev(electricityInvoiceDTO.getVAT()), 2);

        this.totalAmountInEurosWithVatName = formatBigDecimalWithScale(
                calculateTotalAmount(electricityInvoiceDTO.getTotalSumWithoutVAT(), electricityInvoiceDTO.getVAT()), 2
        );

        this.totalAmountInLevsWithVatName = formatBigDecimalWithScale(
                calculateTotalAmount(convertEuroToLev(electricityInvoiceDTO.getTotalSumWithoutVAT()), convertEuroToLev(electricityInvoiceDTO.getVAT())), 2
        );

        this.paymentAmountInEurosName =
                String.format(
                        "%s евро, %d цента",
                        convertNumberToBulgarianWords(calculateTotalAmount(electricityInvoiceDTO.getTotalSumWithoutVAT(), electricityInvoiceDTO.getVAT())
                                .intValue()),
                        calculateTotalAmount(electricityInvoiceDTO.getTotalSumWithoutVAT(), electricityInvoiceDTO.getVAT())
                                .setScale(2, RoundingMode.HALF_UP)
                                .remainder(BigDecimal.ONE)
                                .multiply(BigDecimal.valueOf(100))
                                .intValue()
                );

        this.paymentAmountInLevsName =
                String.format(
                        "%s лева, %d стотинки",
                        convertNumberToBulgarianWords(calculateTotalAmount(convertEuroToLev(electricityInvoiceDTO.getTotalSumWithoutVAT()), convertEuroToLev(electricityInvoiceDTO.getVAT()))
                                .intValue()),
                        calculateTotalAmount(convertEuroToLev(electricityInvoiceDTO.getTotalSumWithoutVAT()), convertEuroToLev(electricityInvoiceDTO.getVAT()))
                                .setScale(2, RoundingMode.HALF_UP)
                                .remainder(BigDecimal.ONE)
                                .multiply(BigDecimal.valueOf(100))
                                .intValue()
                );
    }

    private static String formatBigDecimalWithScale(BigDecimal value, int scale) {
        return value
                .setScale(scale, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');
    }

    private static BigDecimal convertEuroToLev(BigDecimal value) {
        return value.multiply(LEV_EURO_RATIO);
    }

    private BigDecimal calculateTotalAmount(BigDecimal totalSumWithoutVAT, BigDecimal VAT) {
        return totalSumWithoutVAT.add(VAT);
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
            resultList.add(""); // Relationship to invoice
            resultList.add("Електро Енерджи ООД"); // Legal Person Name
            resultList.add("гр. София, бул. България 100"); // Legal Person Address
            resultList.add("123456789"); // Идентификационен № | ЕИК
            resultList.add("BG123456789"); // Идентификационен № по ДДС
            resultList.add("BG12AAAA12341234123456"); // Банкова сметка

            // Получател
            resultList.add("Клиент 1"); // Получател име
            resultList.add("гр. Варна, ул. Морска 10"); // Адрес на получателя
            resultList.add("987654321"); // Идентификационен Номер | ЕИК
            resultList.add("BG987654321"); // Идентификационен № по ДДС

            // Таблица
            resultList.add("Консумирана електроенергия"); // Име на кода на услугата
            resultList.add("Фотоволтаична централа 1"); // Име на централата
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
