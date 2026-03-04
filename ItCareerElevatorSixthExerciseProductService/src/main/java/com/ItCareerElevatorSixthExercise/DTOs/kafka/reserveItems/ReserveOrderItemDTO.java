package com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveOrderItemDTO {

    private String productId; // snowflakeId

    private Integer quantity;
}
