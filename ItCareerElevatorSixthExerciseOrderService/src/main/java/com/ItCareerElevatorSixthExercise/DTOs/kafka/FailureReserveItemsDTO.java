package com.ItCareerElevatorSixthExercise.DTOs.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailureReserveItemsDTO {

    private Long orderId;

    private BigDecimal totalPrice;

    private String reason; // NOT_IN_STOCK, (FAILED_AT_)PROCESSING
}
