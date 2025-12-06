package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowUserRequestDTO {

    private String followerId; // Currently logged-in user, making the request

    private String followedUsername;
}
