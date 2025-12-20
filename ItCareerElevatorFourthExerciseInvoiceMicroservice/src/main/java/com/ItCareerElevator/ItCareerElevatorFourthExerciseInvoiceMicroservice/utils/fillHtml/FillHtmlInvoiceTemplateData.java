package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.fillHtml;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.CurrencyUtils.convertAmountToBulgarianWords;

@Getter
@Setter
@AllArgsConstructor
@NoRepositoryBean
public class FillHtmlInvoiceTemplateData {

    private static final BigDecimal EURO_LEV_RATIO = BigDecimal.valueOf(1.95583);

    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2); // * 20% in Bulgaria

    private final String invoiceNumber; // Random

    private final String generationDate;

    private final String customerUsername;

    private final String customerAddress; // Hardcoded

    private final String customerEik; // Hardcoded

    private final String customerIdentificationNumber; // Hardcoded

    private final List<FillHtmlProductTemplateData> productsTemplateData;

    private final String totalSumWithoutVatInEuros;

    private final String totalSumWithoutVatInLevs;

    private final String vatInEuros;

    private final String vatInLevs;

    private final String totalSumWithVatInEuros;

    private final String totalSumWithVatInLevs;

    private final String totalAmountInEurosName;

    private final String totalAmountInLevsName;

    private final String invoiceCreatorName;

    public FillHtmlInvoiceTemplateData(Order order) {
        this.invoiceNumber = generateRandomInvoiceNumber();
        this.generationDate = formatDateToDateMonthYearFormat(LocalDate.now());

        this.customerUsername = order.getCustomer().getUsername();
        this.customerAddress = "Sofia, ul. Moskovska 31";
        this.customerEik = "BG523464624";
        this.customerIdentificationNumber = "523464624";

        this.productsTemplateData = order
                .getOrderItems()
                .stream()
                .map(FillHtmlProductTemplateData::new).toList();

        BigDecimal TOTAL_SUM_WITHOUT_VAT = order
                .getOrderItems()
                .stream()
                .map(orderItem ->
                        orderItem.getSinglePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalSumWithoutVatInEuros = formatBigDecimalNumberToStringWithScale(TOTAL_SUM_WITHOUT_VAT, 2);
        this.totalSumWithoutVatInLevs = formatBigDecimalNumberToStringWithScale(convertEuroToLev(TOTAL_SUM_WITHOUT_VAT), 2);

        BigDecimal VAT = TOTAL_SUM_WITHOUT_VAT.multiply(VAT_RATE);

        this.vatInEuros = formatBigDecimalNumberToStringWithScale(VAT, 2);
        this.vatInLevs = formatBigDecimalNumberToStringWithScale(VAT, 2);

        BigDecimal TOTAL_SUM_WITH_VAT = TOTAL_SUM_WITHOUT_VAT.add(VAT);

        this.totalSumWithVatInEuros = formatBigDecimalNumberToStringWithScale(TOTAL_SUM_WITH_VAT, 2);
        this.totalSumWithVatInLevs = formatBigDecimalNumberToStringWithScale(convertEuroToLev(TOTAL_SUM_WITH_VAT), 2);

        this.totalAmountInEurosName =
                String.format(
                                "%s евро, %d цента",
                                convertAmountToBulgarianWords(TOTAL_SUM_WITH_VAT.intValue()),
                                TOTAL_SUM_WITH_VAT
                                        .setScale(2, RoundingMode.HALF_UP)
                                        .remainder(BigDecimal.ONE)
                                        .multiply(BigDecimal.valueOf(100))
                                        .intValue()
                        )
                        .replace("един евро", "едно евро") // Catch a case where the method doesn't behave correctly
                        .replace("два евро", "двe евро"); // Catch a case where the method doesn't behave correctly

        this.totalAmountInLevsName =
                String.format(
                        "%s лева, %d стотинки",
                        convertAmountToBulgarianWords(convertEuroToLev(TOTAL_SUM_WITH_VAT).intValue()),
                        convertEuroToLev(TOTAL_SUM_WITH_VAT)
                                .setScale(2, RoundingMode.HALF_UP)
                                .remainder(BigDecimal.ONE)
                                .multiply(BigDecimal.valueOf(100))
                                .intValue()
                );

        this.invoiceCreatorName = "Петър Герджиков";
    }

    public String[] toArray() {
        final int NUMBER_OF_COPIES = 1;
        final int INITIAL_ARRAY_LIST_CAPACITY = NUMBER_OF_COPIES * 16;

        List<String> resultList = new ArrayList<>(INITIAL_ARRAY_LIST_CAPACITY);

        for (int currentCopyNumber = 0; currentCopyNumber < NUMBER_OF_COPIES; ++currentCopyNumber) {
            resultList.add(invoiceNumber);
            resultList.add(generationDate);

            resultList.add(customerUsername);
            resultList.add(customerAddress);
            resultList.add(customerEik);
            resultList.add(customerIdentificationNumber);

            resultList.add(productsTemplateData
                    .stream()
                    .map(FillHtmlProductTemplateData::toString)
                    .collect(Collectors.joining())
            );

            resultList.add(totalSumWithoutVatInEuros);
            resultList.add(totalSumWithoutVatInLevs);
            resultList.add(vatInEuros);
            resultList.add(vatInLevs);
            resultList.add(totalSumWithVatInEuros);
            resultList.add(totalSumWithVatInLevs);
            resultList.add(totalAmountInEurosName);
            resultList.add(totalAmountInLevsName);
            resultList.add(invoiceCreatorName);
        }

        return resultList.toArray(new String[0]);
    }

    private String formatDateToDateMonthYearFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private static String formatBigDecimalNumberToStringWithScale(BigDecimal value, int scale) {
        return value
                .setScale(scale, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');
    }

    private BigDecimal convertEuroToLev(BigDecimal amount) {
        return amount.multiply(EURO_LEV_RATIO);
    }

    public static String generateRandomInvoiceNumber() {
        StringBuilder sb = new StringBuilder(10);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
