package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;

public interface UserService {

    String register(UserRequestDTO userRequest);

    User save(User user);

    String authenticate(String username, String password);
}
