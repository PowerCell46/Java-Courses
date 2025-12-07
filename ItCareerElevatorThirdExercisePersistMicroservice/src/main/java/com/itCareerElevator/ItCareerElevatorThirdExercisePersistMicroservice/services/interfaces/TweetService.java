package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.tweetRelated.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.tweetRelated.LikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.Tweet;

public interface TweetService {

    TweetResponseDTO create(CreateTweetRequestDTO requestDTO);

    Tweet save(Tweet tweet);

    Tweet getBySnowflakeId(String snowflakeId);

    TweetResponseDTO like(LikeTweetRequestDTO requestDTO);

    TweetResponseDTO unlike(LikeTweetRequestDTO requestDTO);
}
