package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDTO {

    private String customerId; // required

    private List<OrderItemRequestDTO> products; // required
}
