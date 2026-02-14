package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> register(RegisterRequestDTO requestDTO) {
        log.info("---> POST request on api/users with username: {}.", requestDTO.getUsername());

        User registeredUser = userService.register(requestDTO);

        return ResponseEntity.created(null).body(registeredUser); // TODO: URL?
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<AlterUserResponseDTO> updateUserDetails(
            PatchUserRequestDTO requestDTO, @PathVariable String userId
    ) {
        log.info("---> Patch request on api/users/{}.", userId);

        var responseDTO = userService.updateFields(userId, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
