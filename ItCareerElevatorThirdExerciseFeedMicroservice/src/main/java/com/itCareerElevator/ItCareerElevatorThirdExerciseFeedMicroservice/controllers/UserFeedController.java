package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.DTOs.UserFeedResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.UserFeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/user-feeds")
@RequiredArgsConstructor
@Slf4j
public class UserFeedController {

    private final UserFeedService userFeedService;

    @GetMapping("/{userSnowflakeId}")
    public ResponseEntity<Collection<UserFeedResponseDTO>> getUserFeed(@PathVariable String userSnowflakeId) {
        log.info("--- GET mapping on /api/user-feeds for user with id: {}.", userSnowflakeId);

        List<UserFeedResponseDTO> userFeed = userFeedService.getFeed(userSnowflakeId);

        return ResponseEntity.ok(userFeed);
    }
}
