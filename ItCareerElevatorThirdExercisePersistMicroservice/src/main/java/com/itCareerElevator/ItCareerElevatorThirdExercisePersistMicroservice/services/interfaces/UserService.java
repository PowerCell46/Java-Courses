package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.UpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;

public interface UserService {

    UserResponseDTO create(CreateUserRequestDTO requestDTO);

    UserResponseDTO follow(FollowUserRequestDTO requestDTO);

    UserResponseDTO unfollow(FollowUserRequestDTO requestDTO);

    User save(User user);

    User getBySnowflakeId(String snowflakeId);

    User getByUsername(String username);

    UserResponseDTO updateUser(UpdateUserRequestDTO requestDTO);
}
