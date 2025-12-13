package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemRequestDTO {

    private String productId; // required

    private Integer quantity; // required
}
