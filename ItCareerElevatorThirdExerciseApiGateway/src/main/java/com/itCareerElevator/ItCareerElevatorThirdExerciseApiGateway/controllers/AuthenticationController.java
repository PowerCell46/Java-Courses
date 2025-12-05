package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.AuthResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRequestDTO userRequest) {
        log.info("--- POST request on /register with username: {}.", userRequest.getUsername());

        var responseDTO = userService.register(userRequest);

        return ResponseEntity.ok(responseDTO); // created is better, but URL...
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody UserRequestDTO userRequest) {
        log.info("--- POST request on /login with username: {}.", userRequest.getUsername());

        var responseDTO = userService
                .authenticate(userRequest.getUsername(), userRequest.getPassword());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/authenticated")
    public String method() {
        User loggedUser = userService.getCurrentlyLoggedUser();

        System.out.printf("Logged in user: %s; Id: %s.%n", loggedUser.getUsername(), loggedUser.getSnowflakeId());

        return "Success GET request on /authenticated.";
    }

    @GetMapping("/role-restricted")
    public String roleRestricted() {
        User loggedUser = userService.getCurrentlyLoggedUser();

        System.out.printf("Logged in user: %s; Id: %s.%n", loggedUser.getUsername(), loggedUser.getSnowflakeId());
        loggedUser.getRoles()
                .forEach(role -> System.out.println("\t- Role: " + role.getName()));

        return "Success GET request on /role-restricted.";
    }
}
