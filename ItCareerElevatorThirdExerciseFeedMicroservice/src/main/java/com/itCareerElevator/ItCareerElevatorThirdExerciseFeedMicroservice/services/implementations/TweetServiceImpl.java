package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.exceptions.NoSuchTweetException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories.TweetRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Override
    public Tweet save(Tweet tweet) {
        log.info("Persisting tweet with content '{}' to the Database.", tweet.getContent());

        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet getBySnowflakeId(String snowflakeId) {
        return tweetRepository
                .findById(CommonEntity.convertSnowflakeIdToId(snowflakeId))
                .orElseThrow(() -> new NoSuchTweetException(String.format("No tweet found with id %s.", snowflakeId)));
    }
}
