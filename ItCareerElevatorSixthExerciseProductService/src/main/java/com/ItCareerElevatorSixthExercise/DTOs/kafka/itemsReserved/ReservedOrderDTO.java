package com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservedOrderDTO {

    private Long orderId; // snowflakeId

    private String userId; // UUID

    private BigDecimal totalPrice;
}
