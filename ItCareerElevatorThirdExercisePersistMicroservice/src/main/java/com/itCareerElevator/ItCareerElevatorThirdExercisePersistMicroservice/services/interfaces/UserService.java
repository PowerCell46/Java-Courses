package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;

public interface UserService {

    UserResponseDTO createUser(CreateUserRequestDTO requestDTO);

    User save(User user);
}
