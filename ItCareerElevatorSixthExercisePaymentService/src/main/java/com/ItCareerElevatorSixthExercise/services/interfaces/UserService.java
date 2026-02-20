package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;

public interface UserService {

    User save(User user);

    boolean isUserWalletAddressPresent(String id);

    UserResponseDTO setWalletAddress(UserRequestDTO requestDTO);
}
