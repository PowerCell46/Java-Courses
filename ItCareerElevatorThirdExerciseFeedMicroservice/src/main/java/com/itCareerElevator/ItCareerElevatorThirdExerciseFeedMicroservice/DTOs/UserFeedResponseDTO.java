package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UserFeedResponseDTO {

    private Collection<TweetResponseDTO> tweets;
}
