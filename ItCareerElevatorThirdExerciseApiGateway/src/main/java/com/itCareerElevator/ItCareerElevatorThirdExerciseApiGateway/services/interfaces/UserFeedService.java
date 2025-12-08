package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;

import java.util.Collection;

public interface UserFeedService {

    Collection<TweetResponseDTO> getUserTweets();
}
