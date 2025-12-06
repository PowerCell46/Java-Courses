package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.AuthResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.persistMicroservice.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    AuthResponseDTO register(UserRequestDTO userRequest);

    User save(User user);

    AuthResponseDTO authenticate(String username, String password);

    User getCurrentlyLoggedUser();

    Optional<User> findByUsername(String username);

    UserResponseDTO follow(FollowUserRequestDTO requestDTO);
}
