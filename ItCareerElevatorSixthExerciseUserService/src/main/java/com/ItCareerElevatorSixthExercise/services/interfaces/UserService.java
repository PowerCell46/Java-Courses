package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.DepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.DepositAmountResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

public interface UserService {

    User save(User user);

    User register(UserRequestDTO requestDTO);

    AlterUserResponseDTO assignRolesToUser(AssignRolesRequestDTO requestDTO);

    AlterUserResponseDTO updateFields(String userId, UserRequestDTO requestDTO);

    void sendSuccessfulRegistrationKafkaMessage(User user);
}
