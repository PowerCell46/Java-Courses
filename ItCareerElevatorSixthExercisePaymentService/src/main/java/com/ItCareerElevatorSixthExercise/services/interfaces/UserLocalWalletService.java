package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserLocalWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserLocalWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

import java.math.BigDecimal;

public interface UserLocalWalletService {

    User save(User user);

    UserResponseDTO initializeUser(UserLocalWalletRequestDTO requestDTO);

    UserLocalWalletResponseDTO processDeposit(String id, BigDecimal depositAmount);

    UserLocalWalletResponseDTO getUserById(String id);
}
