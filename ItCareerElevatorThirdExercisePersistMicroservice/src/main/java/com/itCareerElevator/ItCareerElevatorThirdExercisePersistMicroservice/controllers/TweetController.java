package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponseDTO> createTweet(@RequestBody CreateTweetRequestDTO requestDTO) {
        log.info("--- POST request on /api/tweet.");

        TweetResponseDTO tweetResponseDTO = tweetService.create(requestDTO);

        return ResponseEntity.created(null).body(tweetResponseDTO); // TODO: URL
    }

    // Like

    // Unlike
}
