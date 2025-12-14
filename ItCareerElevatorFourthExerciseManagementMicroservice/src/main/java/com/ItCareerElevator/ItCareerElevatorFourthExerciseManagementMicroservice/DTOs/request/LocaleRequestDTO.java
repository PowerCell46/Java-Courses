package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocaleRequestDTO {

    private String code;

    private String translation;
}
