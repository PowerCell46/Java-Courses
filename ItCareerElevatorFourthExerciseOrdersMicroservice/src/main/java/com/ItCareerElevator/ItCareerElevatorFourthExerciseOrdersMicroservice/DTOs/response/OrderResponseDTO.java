package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDTO {

    @JsonProperty("id")
    private String orderId;

    @JsonProperty("customer")
    private String customerUsername;

    private List<OrderItemResponseDTO> items;
}
