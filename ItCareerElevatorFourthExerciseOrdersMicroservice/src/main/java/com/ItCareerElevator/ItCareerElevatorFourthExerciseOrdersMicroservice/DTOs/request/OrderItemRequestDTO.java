package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemRequestDTO {

    private String productId;

    private Integer quantity;
}
