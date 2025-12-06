package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.Tweet;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.repositories.TweetRepository;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.TweetService;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                tweet.getCreatedBy().getUsername()
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
}
