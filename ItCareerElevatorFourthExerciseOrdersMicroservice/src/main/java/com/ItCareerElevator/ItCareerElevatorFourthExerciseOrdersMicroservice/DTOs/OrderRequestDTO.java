package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDTO {

    private String customerId;

    private List<String> productIds;
}
