package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserFeedService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFeedServiceImpl implements UserFeedService {

    private final WebClient feedServiceWebClient;
    private final UserService userService;

    @Override
    public Collection<TweetResponseDTO> getUserTweets() {
        User loggedUser = userService.getCurrentlyLoggedUser();

        log.info("Making a request to the microservice.");

        return feedServiceWebClient
                .get()
                .uri(String.format("/api/userFeeds/%s", loggedUser.getSnowflakeId()))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(new ParameterizedTypeReference<List<TweetResponseDTO>>() {})
                .block();
    }
}
