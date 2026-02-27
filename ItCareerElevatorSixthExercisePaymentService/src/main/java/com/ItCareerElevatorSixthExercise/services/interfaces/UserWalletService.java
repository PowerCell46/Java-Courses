package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserDepositResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

public interface UserWalletService {

    User save(User user);

    UserResponseDTO initializeUser(UserDepositRequestDTO requestDTO);

    UserDepositResponseDTO processDeposit(UserDepositRequestDTO requestDTO);

    UserDepositResponseDTO getUserById(String id);
}
