package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AuthResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User getCurrentlyLoggedUser();

    User getByUsername(String username);

    AuthResponseDTO register(RegisterRequestDTO userRequest);

    AuthResponseDTO authenticate(String username, String password);

    AlterUserResponseDTO assignRolesToUser(AssignRolesRequestDTO requestDTO);

    AlterUserResponseDTO update(User user, PatchUserRequestDTO userRequest);
}
