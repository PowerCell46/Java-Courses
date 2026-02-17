package com.ItCareerElevatorSixthExercise.DTOs.kafka.reservedItems;

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

    private Long orderId;

    private String userId;

    private BigDecimal totalPrice;
}
