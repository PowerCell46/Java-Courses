package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MsvcFollowUserRequestDTO {

    private String followerId; // Current logged in user

    private String followedUsername;
}
