package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.LikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponseDTO> createTweet(@RequestBody CreateTweetRequestDTO requestDTO) {
        log.info("--- POST request on /api/tweets with content {}.", requestDTO.getContent());

        TweetResponseDTO responseDTO = tweetService.create(requestDTO);
        URI location = URI.create(String.format("/api/tweets/%s", responseDTO.getSnowflakeId()));

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PostMapping("/like")
    public ResponseEntity<TweetResponseDTO> likeTweet(@RequestBody LikeTweetRequestDTO requestDTO) {
        log.info("--- POST request on /api/tweets/like with tweet id {}.", requestDTO.getTweetSnowflakeId());

        TweetResponseDTO responseDTO = tweetService.like(requestDTO);

        return ResponseEntity.created(null).body(responseDTO); // TODO: empty URL
    }

    @DeleteMapping("/like")
    public ResponseEntity<TweetResponseDTO> unlikeTweet(@RequestBody LikeTweetRequestDTO requestDTO) {
        log.info("--- DELETE request on /api/tweets/like with tweet id {}.", requestDTO.getTweetSnowflakeId());

        TweetResponseDTO responseDTO = tweetService.unlike(requestDTO);

        return ResponseEntity.created(null).body(responseDTO); // TODO: empty URL
    }
}
