package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private String username;

    private String token;
}
