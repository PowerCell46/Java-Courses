package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String register(@RequestBody UserRequestDTO userRequest) {
        return userService.register(userRequest);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserRequestDTO request) {
        return userService
                .authenticate(request.getUsername(), request.getPassword());
    }

    @GetMapping("/main")
    public String m() {
        return "Secured response";
    }
}
