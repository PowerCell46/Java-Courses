package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocaleRequestDTO {

    private String code; // required

    private String translation; // required
}
