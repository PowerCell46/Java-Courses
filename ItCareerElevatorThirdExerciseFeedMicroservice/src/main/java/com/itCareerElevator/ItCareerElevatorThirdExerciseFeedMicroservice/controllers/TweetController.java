package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

    private final TweetService tweetService;

    @GetMapping("/{tweetSnowflakeId}")
    public ResponseEntity<TweetResponseDTO> getTweetById(@PathVariable String tweetSnowflakeId) {
        log.info("--- GET request on /api/tweets/{}.", tweetSnowflakeId);

        TweetResponseDTO tweetResponseDTO = tweetService.getTweetBySnowflakeId(tweetSnowflakeId);

        return ResponseEntity.ok(tweetResponseDTO);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<Collection<TweetResponseDTO>> getAllUserTweets(@PathVariable String username) {
        log.info("--- GET request on /api/tweets/profile/{}.", username);

        Collection<TweetResponseDTO> userTweets = tweetService.getAllUserTweets(username);

        return ResponseEntity.ok(userTweets);
    }
}
