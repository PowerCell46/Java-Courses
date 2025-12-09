package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.CreateTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.LikeTweetRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.TweetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetResponseDTO> createTweet(@Valid @RequestBody CreateTweetRequestDTO requestDTO) {
        log.info("--- POST request on /api/tweets with content {}.", requestDTO.getContent());

        TweetResponseDTO tweetResponseDTO = tweetService.create(requestDTO);

        URI location = URI.create(String.format("/api/tweets/%s", tweetResponseDTO.getSnowflakeId()));
        return ResponseEntity.created(location).body(tweetResponseDTO);
    }

    @PostMapping("/like")
    public ResponseEntity<TweetResponseDTO> likeTweet(@Valid @RequestBody LikeTweetRequestDTO requestDTO) {
        log.info("--- POST request on /api/tweets/like with tweet id {}.", requestDTO.getTweetSnowflakeId());

        TweetResponseDTO tweetResponseDTO = tweetService.like(requestDTO);

        URI location = URI.create(String.format("/api/tweets/%s", tweetResponseDTO.getSnowflakeId()));
        return ResponseEntity.created(location).body(tweetResponseDTO);
    }

    @DeleteMapping("/like")
    public ResponseEntity<TweetResponseDTO> unlikeTweet(@Valid @RequestBody LikeTweetRequestDTO requestDTO) {
        log.info("--- DELETE request on /api/tweets/like with tweet id {}.", requestDTO.getTweetSnowflakeId());

        TweetResponseDTO tweetResponseDTO = tweetService.unlike(requestDTO);

        return ResponseEntity.ok(tweetResponseDTO);
    }

    @GetMapping("/{tweetSnowflakeId}")
    public ResponseEntity<TweetResponseDTO> getTweetById(
            @PathVariable("tweetSnowflakeId")
            @NotNull(message = "Tweet id is required.")
            @Pattern(regexp = "^[A-Za-z0-9]{11}$",
                    message = "Tweet id must be exactly 11 alphanumeric characters.")
            String tweetSnowflakeId
    ) {
        log.info("--- GET request on /api/tweets/{}.", tweetSnowflakeId);

        TweetResponseDTO tweetResponseDTO = tweetService.getBySnowflakeId(tweetSnowflakeId);

        return ResponseEntity.ok(tweetResponseDTO);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<Collection<TweetResponseDTO>> getAllUserTweets(
            @PathVariable("username")
            @NotNull(message = "Username is required.")
            @Pattern(
                    regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{5,20}$",
                    message = "Username must be 5-20 characters, contain letters and digits, with at least one uppercase letter and one digit."
            )
            String username
    ) {
        log.info("--- GET request on /api/tweets/profile/{}.", username);

        Collection<TweetResponseDTO> userTweets = tweetService.getAllUserTweets(username);

        return ResponseEntity.ok(userTweets);
    }
}
