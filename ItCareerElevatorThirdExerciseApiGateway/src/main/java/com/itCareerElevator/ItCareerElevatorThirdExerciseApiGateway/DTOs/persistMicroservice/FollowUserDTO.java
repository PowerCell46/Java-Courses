package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.persistMicroservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FollowUserDTO {

    private String followerId;

    private String followedUsername;
}
