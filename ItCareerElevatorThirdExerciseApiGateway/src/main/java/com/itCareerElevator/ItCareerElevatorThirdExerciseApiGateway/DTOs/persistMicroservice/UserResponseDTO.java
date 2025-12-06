package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.persistMicroservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private String id;

    private String username;

    private Collection<String> following;
}
