package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.fillHtml;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.OrderItem;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.ProductTranslation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FillHtmlProductTemplateData {

    private final String productName;

    private final String producerName;

    private final String singlePrice; // Snapshot of the product price when it was bought

    private final String quantity;

    public FillHtmlProductTemplateData(OrderItem orderItem) {
        this.productName = orderItem
                .getProduct()
                .getTranslations()
                .stream()
                .map(ProductTranslation::getName)
                .findFirst()
                .orElse("N/A");

        this.producerName = orderItem
                .getProduct()
                .getProducer()
                .getName();

        this.singlePrice = formatBigDecimalNumberToStringWithScale(
                orderItem.getProduct().getPrice(),
                2
        );

        this.quantity = orderItem
                .getQuantity()
                .toString();
    }

    @Override
    public String toString() {
        return String.format(
                "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                productName,
                producerName,
                singlePrice,
                quantity
        );
    }

    private static String formatBigDecimalNumberToStringWithScale(BigDecimal value, int scale) {
        return value
                .setScale(scale, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');
    }
}
