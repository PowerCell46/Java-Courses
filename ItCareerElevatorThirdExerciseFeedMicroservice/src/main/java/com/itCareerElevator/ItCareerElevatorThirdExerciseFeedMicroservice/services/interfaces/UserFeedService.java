package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.UserFeedResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.UserFeed;

public interface UserFeedService {

    void fanOutTweetToFollowers(String snowflakeId);

    UserFeed save(UserFeed userFeed);

    UserFeedResponseDTO getFeed(String userSnowflakeId);
}
