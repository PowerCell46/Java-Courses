package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.ResetPasswordRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

public interface UserService {

    User save(User user);

    User register(RegisterUserRequestDTO requestDTO);

    AlterUserResponseDTO assignRolesToUser(AssignRolesRequestDTO requestDTO);

    AlterUserResponseDTO updateFields(String userId, PatchUserRequestDTO requestDTO);

    void sendSuccessfulRegistrationKafkaMessage(User user);

    AlterUserResponseDTO resetPassword(String userId, ResetPasswordRequestDTO requestDTO);
}
