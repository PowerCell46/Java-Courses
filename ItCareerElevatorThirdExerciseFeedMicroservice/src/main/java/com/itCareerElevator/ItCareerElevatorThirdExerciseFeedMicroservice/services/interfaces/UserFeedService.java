package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

public interface UserFeedService {

    void fanOutTweetToFollowers(String snowflakeId);
}
