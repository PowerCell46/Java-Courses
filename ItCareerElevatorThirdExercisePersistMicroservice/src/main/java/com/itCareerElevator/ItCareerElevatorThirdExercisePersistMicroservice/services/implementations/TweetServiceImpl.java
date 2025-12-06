package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.LikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.Tweet;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.NoSuchTweetException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.TweetAlreadyLikedException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.repositories.TweetRepository;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.TweetService;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    @Override
    public TweetResponseDTO create(CreateTweetRequestDTO requestDTO) {
        Tweet tweet = constructNonPersistedTweet(requestDTO);
        tweet = save(tweet);

        return new TweetResponseDTO(
                tweet.getSnowflakeId(),
                tweet.getContent(),
                tweet.getCreatedBy().getUsername(),
                tweet.getLikedBy().stream().map(User::getUsername).toList()
        );
    }

    private Tweet constructNonPersistedTweet(CreateTweetRequestDTO requestDTO) {
        User createdBy = userService.getBySnowflakeId(requestDTO.getUserSnowflakeId());

        return new Tweet(requestDTO.getContent(), createdBy);
    }

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

    @Override
    @Transactional
    public TweetResponseDTO like(LikeTweetRequestDTO requestDTO) {
        User user = userService.getBySnowflakeId(requestDTO.getUserSnowflakeId());
        Tweet tweet = getBySnowflakeId(requestDTO.getTweetSnowflakeId());

        if (user.getLikedTweets().contains(tweet)) {
             throw new TweetAlreadyLikedException(String.format(
                     "User %s has already liked the tweet with id %s.",
                     user.getUsername(),
                     tweet.getSnowflakeId()
             ));
        }

        user.getLikedTweets().add(tweet);
        tweet.getLikedBy().add(user);

        userService.save(user);

        return new TweetResponseDTO(
                tweet.getSnowflakeId(),
                tweet.getContent(),
                tweet.getCreatedBy().getUsername(),
                tweet.getLikedBy().stream().map(User::getUsername).toList()
        );
    }
}
