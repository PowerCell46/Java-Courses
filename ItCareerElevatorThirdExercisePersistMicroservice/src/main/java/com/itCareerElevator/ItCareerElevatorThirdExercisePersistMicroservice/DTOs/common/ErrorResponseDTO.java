package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    private Integer status;

    private String message;

    private Long timestamp;
}
