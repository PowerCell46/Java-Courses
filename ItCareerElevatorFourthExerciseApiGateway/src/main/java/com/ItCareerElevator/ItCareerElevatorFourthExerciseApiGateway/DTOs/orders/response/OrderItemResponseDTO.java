package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponseDTO {

    private String productName;

    private Integer quantity;

    @JsonProperty("price")
    private BigDecimal singlePrice;
}
