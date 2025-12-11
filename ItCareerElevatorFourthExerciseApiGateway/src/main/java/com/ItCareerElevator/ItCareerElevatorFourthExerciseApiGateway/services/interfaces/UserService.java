package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.auth.AuthRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.auth.AuthResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    AuthResponseDTO register(AuthRequestDTO userRequest);

    User save(User user);

    AuthResponseDTO authenticate(String username, String password);

    User getCurrentlyLoggedUser();

    Optional<User> findByUsername(String username);
}
