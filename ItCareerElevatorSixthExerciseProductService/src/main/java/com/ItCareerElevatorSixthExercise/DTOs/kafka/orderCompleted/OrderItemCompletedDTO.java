package com.ItCareerElevatorSixthExercise.DTOs.kafka.orderCompleted;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCompletedDTO {

    private String id; // snowflakeId

    private Integer quantity;

    private BigDecimal price;
}
