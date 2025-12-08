package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.LikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;

import java.util.Collection;

public interface TweetService {

    TweetResponseDTO getBySnowflakeId(String snowflakeId);

    Collection<TweetResponseDTO> getAllUserTweets(String userSnowflakeId);

    TweetResponseDTO create(CreateTweetRequestDTO requestDTO);

    TweetResponseDTO like(LikeTweetRequestDTO requestDTO);

    TweetResponseDTO unlike(LikeTweetRequestDTO requestDTO);
}
