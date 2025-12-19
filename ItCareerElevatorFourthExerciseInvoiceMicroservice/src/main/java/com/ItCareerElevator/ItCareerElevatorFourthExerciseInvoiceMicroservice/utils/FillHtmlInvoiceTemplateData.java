package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.CurrencyUtils.convertAmountToBulgarianWords;

@Getter
@Setter
@AllArgsConstructor
@NoRepositoryBean
public class FillHtmlInvoiceTemplateData {

    private final String invoiceNumber;

    private final String generationDate;

    private final String customerUsername;

    private final String customerAddress;

    private final String customerEik;

    private final String customerIdentificationNumber;

    private final List<FillHtmlProductTemplateData> productTemplatesData;

    private final String totalSumWithoutVAT;

    private final String VAT;

    private final String totalSumWithVAT;

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

        BigDecimal totalSumWithoutVat = order.getOrderItems().stream()
                .map(orderItem -> orderItem.getSinglePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalSumWithoutVAT = formatBigDecimalWithScale(totalSumWithoutVat, 2);
        this.VAT = formatBigDecimalWithScale(totalSumWithoutVat.multiply(BigDecimal.valueOf(0.2)), 2);

        final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2);
        BigDecimal totalSumWithVat = totalSumWithoutVat.add(totalSumWithoutVat.multiply(VAT_RATE));

        this.totalSumWithVAT = formatBigDecimalWithScale(totalSumWithVat, 2);

        this.totalAmountInEurosName =
                String.format(
                        "%s евро, %d цента",
                        convertAmountToBulgarianWords(totalSumWithVat.intValue()),
                        totalSumWithVat
                                .setScale(2, RoundingMode.HALF_UP)
                                .remainder(BigDecimal.ONE)
                                .multiply(BigDecimal.valueOf(100))
                                .intValue()
                );

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
        final int NUMBER_OF_COPIES = 2;

        List<String> resultList = new LinkedList<>(); // ? Maybe better than ArrayList

        for (int currentCopyNumber = 0; currentCopyNumber < NUMBER_OF_COPIES; ++currentCopyNumber) {
            resultList.add(invoiceNumber);
            resultList.add(generationDate);
            resultList.add(customerUsername);
            resultList.add(customerAddress);
            resultList.add(customerEik);
            resultList.add(customerIdentificationNumber);
            resultList.add(productTemplatesData.stream().map(FillHtmlProductTemplateData::toString).collect(Collectors.joining()));
            resultList.add(totalSumWithoutVAT);
            resultList.add(VAT);
            resultList.add(totalSumWithVAT);
            resultList.add(totalAmountInEurosName);
            resultList.add(totalAmountInLevsName);
        }

        return resultList.toArray(new String[resultList.size()]);
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
