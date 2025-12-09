package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.FeedMicroserviceException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserFeedService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
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

        log.info("Making a request to the feed microservice.");

        return feedServiceWebClient
                .get()
                .uri(String.format("/api/userFeeds/%s", loggedUser.getSnowflakeId()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(FeedMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(new ParameterizedTypeReference<List<TweetResponseDTO>>() {})
                .block();
    }
}
