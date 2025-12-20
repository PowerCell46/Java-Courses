package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDTO {

    private String id;

    private String customerUsername;

    private List<OrderItemResponseDTO> items;
}
