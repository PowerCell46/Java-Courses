package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String username;

    private String password;
}
// TODO: validations