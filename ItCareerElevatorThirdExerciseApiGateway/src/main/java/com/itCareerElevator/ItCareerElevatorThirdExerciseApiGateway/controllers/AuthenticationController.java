package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.AuthenticationResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody UserRequestDTO userRequest) {
        log.info("--- POST request on /register with username: {}.", userRequest.getUsername());

        var responseDTO = userService.register(userRequest);

        return ResponseEntity.ok(responseDTO); // created is better, but URL...
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody UserRequestDTO userRequest) {
        log.info("--- POST request on /login with username: {}.", userRequest.getUsername());

        var responseDTO = userService
                .authenticate(userRequest.getUsername(), userRequest.getPassword());

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/main")
    public String m(@AuthenticationPrincipal UserDetails user) {
        System.out.println("/main: " + user.getUsername());

        return "Secured response";
    }
}
