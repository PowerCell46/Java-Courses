package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;

public interface UserService {

    UserResponseDTO create(CreateUserRequestDTO requestDTO);

    UserResponseDTO follow(FollowUserRequestDTO requestDTO);

    User save(User user);

    User getBySnowflakeId(String snowflakeId);
}
