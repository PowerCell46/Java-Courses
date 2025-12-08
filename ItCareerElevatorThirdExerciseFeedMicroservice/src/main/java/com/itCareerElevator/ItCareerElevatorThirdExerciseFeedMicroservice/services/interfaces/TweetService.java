package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;

import java.util.Collection;

public interface TweetService {

    Tweet save(Tweet tweet);

    Tweet getBySnowflakeId(String snowflakeId);

    TweetResponseDTO getTweetBySnowflakeId(String snowflakeId);

    Collection<TweetResponseDTO> getAllUserTweets(String username);
}
