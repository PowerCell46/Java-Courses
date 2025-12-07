package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequestDTO { // TODO: validations

    private String username;

    private String password;
}
