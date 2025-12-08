package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.UserFeedResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.Tweet;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.UserFeed;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories.UserFeedRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.TweetService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.UserFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFeedServiceImpl implements UserFeedService {

    private final UserFeedRepository userFeedRepository;
    private final TweetService tweetService;

    @Override
    public void fanOutTweetToFollowers(String snowflakeId) {
        cleanupSeenTweets();

        Tweet tweet = tweetService.getBySnowflakeId(snowflakeId);
        Set<User> followers = tweet.getCreatedBy().getFollowers();

        followers
                .stream()
                .map(follower -> constructNonPersistedUserFeed(follower, tweet))
                .forEach(this::save);
    }

    private void cleanupSeenTweets() {
        log.info("Cleaning up already seen tweets from users.");
        Set<UserFeed> seenUserTweets = userFeedRepository.findAllByHasBeenSeen(true);
        userFeedRepository.deleteAll(seenUserTweets);
    }

    private UserFeed constructNonPersistedUserFeed(User follower, Tweet tweet) {
        return new UserFeed(follower, tweet);
    }

    @Override
    public UserFeed save(UserFeed userFeed) {
        log.info("Saving userFeed with username {} and tweet id {} to the Database.",
                userFeed.getUser().getUsername(),
                userFeed.getTweet().getSnowflakeId()
        );

        return userFeedRepository.save(userFeed);
    }

    @Override
    public List<UserFeedResponseDTO> getFeed(String userSnowflakeId) {
        log.info("Fetching feed for user with snowflakeId: {}", userSnowflakeId);
        Set<UserFeed> userFeedSet = userFeedRepository
                .findAllByUserId(CommonEntity.convertSnowflakeIdToId(userSnowflakeId));

        for (UserFeed feed: userFeedSet) {
            feed.setHasBeenSeen(true);
        }

        userFeedRepository.saveAll(userFeedSet);

        return userFeedSet
                .stream()
                .map(this::constructUserFeedResponseDTO)
                .toList();
    }

    private UserFeedResponseDTO constructUserFeedResponseDTO(UserFeed userFeed) {
        return new UserFeedResponseDTO(
                userFeed.getTweet().getSnowflakeId(),
                userFeed.getTweet().getContent()
        );
    }
}
