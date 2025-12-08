package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.UserFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/userFeeds")
@RequiredArgsConstructor
@Slf4j
public class UserFeedController {

    private final UserFeedService userFeedService;

    @GetMapping("/{userSnowflakeId}")
    public ResponseEntity<Collection<TweetResponseDTO>> getUserFeed(@PathVariable String userSnowflakeId) {
        log.info("--- GET mapping on /api/user-feeds for user with id: {}.", userSnowflakeId);

        Collection<TweetResponseDTO> userFeed = userFeedService.getFeed(userSnowflakeId);

        return ResponseEntity.ok(userFeed);
    }
}
