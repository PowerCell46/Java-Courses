package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.OrderItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FillHtmlProductTemplateData {

    private final String productName;

    private final String singlePrice;

    private final String quantity;

    public FillHtmlProductTemplateData(OrderItem orderItem) {
        this.productName = orderItem.getProduct().getTranslations().stream().findFirst().get().getName();
        this.singlePrice = formatBigDecimalWithScale(orderItem.getProduct().getPrice(), 2);
        this.quantity = orderItem.getQuantity().toString();
    }

    @Override
    public String toString() {
        return String.format(
                "<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                productName,
                singlePrice,
                quantity
        );
    }

    private static String formatBigDecimalWithScale(BigDecimal value, int scale) {
        return value
                .setScale(scale, RoundingMode.HALF_UP)
                .toString()
                .replace('.', ',');
    }
}
