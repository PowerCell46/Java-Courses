package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.TweetService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.UserFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFeedServiceImpl implements UserFeedService {

    private final TweetService tweetService;

    @Override
    public void fanOutTweetToFollowers(String snowflakeId) {
        Tweet tweet = tweetService.getBySnowflakeId(snowflakeId);


    }
}
