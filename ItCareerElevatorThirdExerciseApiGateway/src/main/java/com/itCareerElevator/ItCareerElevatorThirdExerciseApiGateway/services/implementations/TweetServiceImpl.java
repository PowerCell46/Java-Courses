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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TweetServiceImpl implements TweetService {

    private final WebClient persistenceServiceWebClient;
    private final UserService userService;

    @Override
    public TweetResponseDTO getBySnowflakeId(String snowflakeId) {
        log.info("Making a request to the microservice.");

        return persistenceServiceWebClient
                .get()
                .uri(String.format("/api/tweets/%s", snowflakeId))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(TweetResponseDTO.class)
                .block();
    }

    @Override
    public Collection<TweetResponseDTO> getAllUserTweets(String username) {
        log.info("Making a request to the microservice.");

        return persistenceServiceWebClient
                .get()
                .uri(String.format("/api/tweets/profile/%s", username))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(new ParameterizedTypeReference<List<TweetResponseDTO>>() {})
                .block();
    }

    @Override
    public TweetResponseDTO create(CreateTweetRequestDTO requestDTO) {
        User loggedUser = userService.getCurrentlyLoggedUser();

        log.info("Making a request to the microservice.");
        return persistenceServiceWebClient
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
        return persistenceServiceWebClient
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
        return persistenceServiceWebClient
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
