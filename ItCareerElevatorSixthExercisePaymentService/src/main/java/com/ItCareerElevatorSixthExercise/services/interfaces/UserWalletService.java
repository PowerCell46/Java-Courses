package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserLocalWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserLocalWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

public interface UserWalletService {

    User save(User user);

    UserResponseDTO initializeUser(UserLocalWalletRequestDTO requestDTO);

    UserLocalWalletResponseDTO processDeposit(UserLocalWalletRequestDTO requestDTO);

    UserLocalWalletResponseDTO getUserById(String id);
}
