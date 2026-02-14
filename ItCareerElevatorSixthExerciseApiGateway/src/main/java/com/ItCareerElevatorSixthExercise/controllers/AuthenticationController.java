package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.LoginRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AuthResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO userRequest) {
        log.info("---> POST request on api/auth/register with username: {}.", userRequest.getUsername());

        var responseDTO = userService.register(userRequest);

        return ResponseEntity.created(null).body(responseDTO); // TODO: URL
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO userRequest) {
        log.info("---> POST request on api/auth/login with username: {}.", userRequest.getUsername());

        var responseDTO = userService.authenticate(userRequest.getUsername(), userRequest.getPassword());

        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/profile")
    public ResponseEntity<AlterUserResponseDTO> updateUser(@Valid @RequestBody PatchUserRequestDTO userRequest) {
        User loggedUser = userService.getCurrentlyLoggedUser();
        log.info("---> PATCH request on api/auth/profile for user {}.", loggedUser.getUsername());

        var responseDTO = userService.update(loggedUser, userRequest);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/assign-roles")
    public ResponseEntity<AlterUserResponseDTO> assignRolesToUser(@Valid @RequestBody AssignRolesRequestDTO requestDTO) {
        log.info("---> POST request on api/auth/assign-roles for user with username: {}.", requestDTO.getUsername());

        var responseDTO = userService.assignRolesToUser(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
