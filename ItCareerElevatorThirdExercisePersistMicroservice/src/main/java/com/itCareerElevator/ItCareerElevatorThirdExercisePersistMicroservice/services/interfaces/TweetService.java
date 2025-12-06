package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.Tweet;

public interface TweetService {

    TweetResponseDTO create(CreateTweetRequestDTO requestDTO);

    Tweet save(Tweet tweet);
}
