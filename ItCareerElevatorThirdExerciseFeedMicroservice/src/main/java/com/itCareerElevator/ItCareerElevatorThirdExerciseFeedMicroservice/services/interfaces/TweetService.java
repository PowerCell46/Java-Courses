package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;

public interface TweetService {

    Tweet save(Tweet tweet);

    Tweet getBySnowflakeId(String snowflakeId);

}
