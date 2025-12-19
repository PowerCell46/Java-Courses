package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponseDTO {

    private String productId;

    private BigDecimal singlePrice;

    private Integer quantity;
}
