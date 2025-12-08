package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.UserFeedResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.UserFeed;

import java.util.List;

public interface UserFeedService {

    void fanOutTweetToFollowers(String snowflakeId);

    UserFeed save(UserFeed userFeed);

    List<UserFeedResponseDTO> getFeed(String userSnowflakeId);
}
