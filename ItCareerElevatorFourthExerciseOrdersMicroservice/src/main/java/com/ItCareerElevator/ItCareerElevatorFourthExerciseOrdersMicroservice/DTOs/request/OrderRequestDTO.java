package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDTO {

    @JsonProperty("customerId")
    private String userId;

    private List<OrderItemRequestDTO> products;
}
