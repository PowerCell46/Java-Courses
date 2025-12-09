package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowUserRequestDTO {

    private String followerId; // Currently logged-in user

    private String followedUsername;
}
