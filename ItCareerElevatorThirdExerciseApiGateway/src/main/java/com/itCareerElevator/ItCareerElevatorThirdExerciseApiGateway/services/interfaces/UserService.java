package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated.AuthResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UserFollowRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated.AuthRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    AuthResponseDTO register(AuthRequestDTO userRequest);

    User save(User user);

    AuthResponseDTO authenticate(String username, String password);

    User getCurrentlyLoggedUser();

    Optional<User> findByUsername(String username);

    UserResponseDTO follow(UserFollowRequestDTO requestDTO);

    UserResponseDTO unfollow(UserFollowRequestDTO requestDTO);

    UserResponseDTO update(UpdateUserRequestDTO userRequestDTO);
}
