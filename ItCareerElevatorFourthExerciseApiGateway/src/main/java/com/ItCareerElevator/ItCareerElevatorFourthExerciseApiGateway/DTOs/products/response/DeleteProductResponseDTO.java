package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteProductResponseDTO {

    private String id;

    @JsonProperty("name")
    private String productName;
}
