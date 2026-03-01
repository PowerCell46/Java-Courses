package com.ItCareerElevatorSixthExercise.DTOs.kafka.failureReserveItems;

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

    private Long orderId; // snowflakeId

    private BigDecimal totalPrice;

    private String reason;
}
