package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.AuthenticationResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;

public interface UserService {

    AuthenticationResponseDTO register(UserRequestDTO userRequest);

    User save(User user);

    AuthenticationResponseDTO authenticate(String username, String password);

    User getCurrentlyLoggedUser();
}
