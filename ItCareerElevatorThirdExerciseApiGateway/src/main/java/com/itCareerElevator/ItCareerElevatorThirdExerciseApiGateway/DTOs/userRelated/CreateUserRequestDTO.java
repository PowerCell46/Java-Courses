package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserRequestDTO {

    private String id;

    private String username;
}
