package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO { // TODO: validations

    private String username;

    private String password;
}
