package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated.TweetResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UserFollowRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserFeedService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;
    private final UserFeedService userFeedService;

    @PatchMapping
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserRequestDTO userRequestDTO) {
        log.info("--- PATCH request on api/users.");

        UserResponseDTO responseDTO = userService.update(userRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/follow")
    public ResponseEntity<UserResponseDTO> followUser(@RequestBody UserFollowRequestDTO userRequestDTO) {
        log.info("--- POST request on api/users/follow with (following) username {}.", userRequestDTO.getUsername());

        UserResponseDTO responseDTO = userService.follow(userRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/follow")
    public ResponseEntity<UserResponseDTO> unfollowUser(@RequestBody UserFollowRequestDTO userRequestDTO) {
        log.info("--- DELETE request on api/users/follow with username {}.", userRequestDTO.getUsername());

        UserResponseDTO responseDTO = userService.unfollow(userRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/feed")
    public ResponseEntity<Collection<TweetResponseDTO>> getUserFeed() {
        log.info("--- GET request on /api/users/feed.");

        Collection<TweetResponseDTO> userTweets = userFeedService.getUserTweets();

        return ResponseEntity.ok(userTweets);
    }
}
