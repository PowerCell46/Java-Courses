package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;

import java.util.Collection;

public interface TweetService {

    Tweet getBySnowflakeId(String snowflakeId);

    TweetResponseDTO getTweetResponseDTOBySnowflakeId(String snowflakeId);

    Collection<TweetResponseDTO> getAllUserTweets(String username);
}
