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

    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2);

    private final String invoiceNumber;

    private final String generationDate;

    private final String customerUsername;

    private final String customerAddress;

    private final String customerEik;

    private final String customerIdentificationNumber;

    private final List<FillHtmlProductTemplateData> productTemplatesData;

    private final String totalSumWithoutVATInEuros;

    private final String totalSumWithoutVATInLevs;

    private final String VatInEuros;

    private final String VatInLevs;

    private final String totalSumWithVatInEuros;

    private final String totalSumWithVatInLevs;

    private final String totalAmountInEurosName;

    private final String totalAmountInLevsName;

    public FillHtmlInvoiceTemplateData(Order order) {
        this.invoiceNumber = generateRandomInvoiceNumber();
        this.generationDate = formatDateToDateMonthYear(LocalDate.now());

        this.customerUsername = order.getCustomer().getUsername();
        this.customerAddress = "Sofia, ul. Moskovska 31";
        this.customerEik = "BG523464624";
        this.customerIdentificationNumber = "523464624";

        this.productTemplatesData = order
                .getOrderItems()
                .stream()
                .map(FillHtmlProductTemplateData::new).toList();

        BigDecimal totalSumWithoutVat = order
                .getOrderItems()
                .stream()
                .map(orderItem ->
                        orderItem.getSinglePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalSumWithoutVATInEuros = formatBigDecimalWithScale(totalSumWithoutVat, 2);
        this.totalSumWithoutVATInLevs = formatBigDecimalWithScale(totalSumWithoutVat.multiply(BigDecimal.valueOf(1.95583)), 2);

        this.VatInEuros = formatBigDecimalWithScale(totalSumWithoutVat.multiply(VAT_RATE), 2);

        this.VatInLevs = formatBigDecimalWithScale(totalSumWithoutVat.multiply(VAT_RATE).multiply(BigDecimal.valueOf(1.95593)), 2);

        BigDecimal totalSumWithVat = totalSumWithoutVat.add(totalSumWithoutVat.multiply(VAT_RATE));

        this.totalSumWithVatInEuros = formatBigDecimalWithScale(totalSumWithVat, 2);

        this.totalSumWithVatInLevs = formatBigDecimalWithScale(totalSumWithVat.multiply(BigDecimal.valueOf(1.95593)), 2);

        this.totalAmountInEurosName =
                String.format(
                                "%s евро, %d цента",
                                convertAmountToBulgarianWords(totalSumWithVat.intValue()),
                                totalSumWithVat
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
                        convertAmountToBulgarianWords(totalSumWithVat.multiply(BigDecimal.valueOf(1.95583)).intValue()),
                        totalSumWithVat.multiply(BigDecimal.valueOf(1.95583))
                                .setScale(2, RoundingMode.HALF_UP)
                                .remainder(BigDecimal.ONE)
                                .multiply(BigDecimal.valueOf(100))
                                .intValue()
                );
    }

    public String[] toArray() {
        final int NUMBER_OF_COPIES = 1;
        final int INITIAL_ARRAY_LIST_CAPACITY = NUMBER_OF_COPIES * 15;

        List<String> resultList = new ArrayList<>(INITIAL_ARRAY_LIST_CAPACITY);

        for (int currentCopyNumber = 0; currentCopyNumber < NUMBER_OF_COPIES; ++currentCopyNumber) {
            resultList.add(invoiceNumber);
            resultList.add(generationDate);

            resultList.add(customerUsername);
            resultList.add(customerAddress);
            resultList.add(customerEik);
            resultList.add(customerIdentificationNumber);

            resultList.add(productTemplatesData.stream().map(FillHtmlProductTemplateData::toString).collect(Collectors.joining()));

            resultList.add(totalSumWithoutVATInEuros);
            resultList.add(totalSumWithoutVATInLevs);
            resultList.add(VatInEuros);
            resultList.add(VatInLevs);
            resultList.add(totalSumWithVatInEuros);
            resultList.add(totalSumWithVatInLevs);
            resultList.add(totalAmountInEurosName);
            resultList.add(totalAmountInLevsName);
        }

        return resultList.toArray(new String[0]);
    }

    private String formatDateToDateMonthYear(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private static String formatBigDecimalWithScale(BigDecimal value, int scale) {
        return value
                .setScale(scale, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');
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
