package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.Role;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.EmailIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.UsernameIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.RoleService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterRequestDTO requestDTO) {
        validateRegisterData(requestDTO);

        User user = new User(
                requestDTO.getUsername(),
                requestDTO.getEmail(),
                encodeUserPassword(requestDTO.getPassword())
        );

        return save(user);
    }

    private void validateRegisterData(RegisterRequestDTO userRequest) {
//        if (findByUsername(userRequest.getUsername()).isPresent()) {
//            throw new UsernameIsAlreadyTakenException(
//                    String.format("User with username %s already exists.", userRequest.getUsername())
//            );
//        }
//
//        if (findByEmail(userRequest.getEmail()).isPresent()) {
//            throw new EmailIsAlreadyTakenException(
//                    String.format("Email %s is already taken.", userRequest.getEmail())
//            );
//        }
    }

    public String encodeUserPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username {} to the database.", user.getUsername());

        return userRepository.save(user);
    }

    @Override
    public AlterUserResponseDTO assignRolesToUser(AssignRolesRequestDTO requestDTO) {
        User user = getByUsername(requestDTO.getUsername());

        requestDTO
                .getRoles()
                .forEach(roleName -> {
                    Role role = roleService.getByName(roleName);
                    user.getRoles().add(role);
                });
        save(user);

        return new AlterUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    private User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with username %s.", username)));
    }

    @Override
    public AlterUserResponseDTO updateFields(String userId, PatchUserRequestDTO requestDTO) {
        User user = userRepository
                .findById(userId)
                .orElseThrow((() -> new NoSuchUserException(String.format("No user found with id %s.", userId))));

        if (requestDTO.getUsername() != null) {
            if (userRepository.findByUsername(requestDTO.getUsername()).isPresent()) {
                throw new UsernameIsAlreadyTakenException(
                        String.format("User with username %s already exists.", requestDTO.getUsername())
                );
            }
            user.setUsername(requestDTO.getUsername());
        }

        if (requestDTO.getEmail() != null) {
            if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                throw new EmailIsAlreadyTakenException(
                        String.format("Email %s is already taken.", requestDTO.getEmail()));
            }
            user.setEmail(requestDTO.getEmail());
        }

        if (requestDTO.getPassword() != null) { // ? Normally this would happen by a link sent to the email for resetting the password
            user.setPassword(encodeUserPassword(requestDTO.getPassword()));
        }

        if (requestDTO.getUsername() != null || requestDTO.getEmail() != null || requestDTO.getPassword() != null) {
            log.info("Updating user with username {}.", user.getUsername());
            save(user);
        }

        return new AlterUserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
