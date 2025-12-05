package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.UserService;
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

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO requestDTO) {
        log.info("--- POST request on /api/users.");

        UserResponseDTO userResponseDTO = userService.createUser(requestDTO);

        return ResponseEntity.created(null).body(userResponseDTO);
    }

    @PostMapping("/follow")
    public ResponseEntity<UserResponseDTO> followUser(@RequestBody FollowUserRequestDTO requestDTO) {
        log.info("--- POST request on /api/users/follow.");

        UserResponseDTO responseDTO = userService.followUser(requestDTO);

        return ResponseEntity.created(null).body(responseDTO);
    }

    // PatchMapping
    // set the other fields (all should be optional)
}
