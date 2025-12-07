package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FollowUserRequestDTO {

    private String followerId;

    private String followedUsername;
}
