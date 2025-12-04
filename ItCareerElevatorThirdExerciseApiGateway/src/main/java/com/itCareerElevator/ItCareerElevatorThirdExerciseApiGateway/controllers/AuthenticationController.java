package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.LoginRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.repositories.UserRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations.UserDetailsServiceImpl;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private AuthenticationManager authenticationManager;

    private UserDetailsServiceImpl userDetailsService;

    private UserRepository userRepository;

    private PasswordEncoder encoder;

    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully.";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequestDTO request) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        ));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @GetMapping("/main")
    public String m() {
        return "Secured response";
    }
}
