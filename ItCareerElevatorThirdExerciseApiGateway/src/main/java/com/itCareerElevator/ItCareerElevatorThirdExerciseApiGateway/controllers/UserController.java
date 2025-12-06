package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/follow")
    public ResponseEntity<UserResponseDTO> followUser(@RequestBody FollowUserRequestDTO userRequestDTO) {
        log.info("--- POST request on api/users/follow with username {}.", userRequestDTO.getUsername());

        UserResponseDTO responseDTO = userService.follow(userRequestDTO);

        return ResponseEntity.created(null).body(responseDTO); // TODO: Empty URL
    }
}
