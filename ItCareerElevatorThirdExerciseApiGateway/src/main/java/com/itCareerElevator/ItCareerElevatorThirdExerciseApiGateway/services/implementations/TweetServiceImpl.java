package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.LikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.MsvcCreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.MsvcLikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.TweetService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TweetServiceImpl implements TweetService {

    private final WebClient tweetServiceWebClient;
    private final UserService userService;

    @Override
    public TweetResponseDTO create(CreateTweetRequestDTO requestDTO) {
        User loggedUser = userService.getCurrentlyLoggedUser();

        log.info("Making a request to the microservice.");
        return tweetServiceWebClient
                .post()
                .uri("/api/tweets")
                .bodyValue(new MsvcCreateTweetRequestDTO(
                        loggedUser.getSnowflakeId(),
                        requestDTO.getContent()
                ))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(TweetResponseDTO.class)
                .block();
    }

    @Override
    public TweetResponseDTO like(LikeTweetRequestDTO requestDTO) {
        User loggedUser = userService.getCurrentlyLoggedUser();

        log.info("Making a request to the microservice.");
        return tweetServiceWebClient
                .post()
                .uri("/api/tweets/like")
                .bodyValue(new MsvcLikeTweetRequestDTO(
                        loggedUser.getSnowflakeId(),
                        requestDTO.getTweetSnowflakeId()
                ))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(TweetResponseDTO.class)
                .block();
    }

    @Override
    public TweetResponseDTO unlike(LikeTweetRequestDTO requestDTO) {
        User loggedUser = userService.getCurrentlyLoggedUser();

        log.info("Making a request to the microservice.");
        return tweetServiceWebClient
                .method(HttpMethod.DELETE)
                .uri("/api/tweets/like")
                .bodyValue(new MsvcLikeTweetRequestDTO(
                        loggedUser.getSnowflakeId(),
                        requestDTO.getTweetSnowflakeId()
                ))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(TweetResponseDTO.class)
                .block();
    }
}
