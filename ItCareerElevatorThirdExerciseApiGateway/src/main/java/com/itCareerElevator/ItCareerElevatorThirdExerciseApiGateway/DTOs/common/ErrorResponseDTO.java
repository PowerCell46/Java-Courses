package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponseDTO {

    private Integer status;

    private String message;

    private Long timestamp;
}
