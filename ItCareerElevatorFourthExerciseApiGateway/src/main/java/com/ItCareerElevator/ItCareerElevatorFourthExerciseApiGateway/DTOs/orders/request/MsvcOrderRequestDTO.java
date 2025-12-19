package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MsvcOrderRequestDTO {

    @JsonProperty("customerId")
    private String currentUserId;

    private List<OrderItemRequestDTO> products;
}
