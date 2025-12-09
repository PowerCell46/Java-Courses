package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.exceptions.NoSuchTweetException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories.TweetRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    @Override
    public Tweet getBySnowflakeId(String snowflakeId) {
        return tweetRepository
                .findById(CommonEntity.convertSnowflakeIdToId(snowflakeId))
                .orElseThrow(() -> new NoSuchTweetException(String.format("No tweet found with id %s.", snowflakeId)));
    }

    @Override
    public TweetResponseDTO getTweetResponseDTOBySnowflakeId(String snowflakeId) {
        Tweet tweet = getBySnowflakeId(snowflakeId);

        return constructTweetResponseDTO(tweet);
    }

    private TweetResponseDTO constructTweetResponseDTO(Tweet tweet) {
        return new TweetResponseDTO(
                tweet.getSnowflakeId(),
                tweet.getContent(),
                tweet.getCreatedBy().getUsername(),
                tweet.getLikedBy()
                        .stream()
                        .map(User::getUsername)
                        .toList()
        );
    }

    @Override
    public Collection<TweetResponseDTO> getAllUserTweets(String username) {
        List<Tweet> userTweets = tweetRepository
                .findAllByCreatedByUsernameOrderByLastModifiedAtDesc(username);

        return userTweets
                .stream()
                .map(this::constructTweetResponseDTO)
                .toList();
    }
}
