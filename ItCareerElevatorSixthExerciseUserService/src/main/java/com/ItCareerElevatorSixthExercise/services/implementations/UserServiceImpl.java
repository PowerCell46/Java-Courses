package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.ResetPasswordRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.kafka.UserRegisteredDTO;
import com.ItCareerElevatorSixthExercise.entities.Role;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.user.EmailIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.exceptions.user.InvalidCredentialsException;
import com.ItCareerElevatorSixthExercise.exceptions.user.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.user.UsernameIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.PaymentService;
import com.ItCareerElevatorSixthExercise.services.interfaces.RoleService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.kafka.topics.user-registered}")
    private String USER_REGISTERED_TOPIC_NAME;

    private final RoleService roleService;
    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, String> registerEmailKafkaTemplate;

    @Override
    public User register(RegisterUserRequestDTO requestDTO) {
        validateRegisterData(requestDTO);

        User user = new User(
                requestDTO.getUsername(),
                requestDTO.getEmail(),
                encodePassword(requestDTO.getPassword())
        );
        user = save(user);

        /* if (requestDTO.getWalletAddress() != null) {
            paymentService.initializeCryptoWalletAddress(user.getId(), requestDTO.getWalletAddress());
        }
        */

        sendSuccessfulRegistrationKafkaMessage(user);
        return user;
    }

    private void validateRegisterData(RegisterUserRequestDTO userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new UsernameIsAlreadyTakenException(
                    String.format("User with username %s already exists.", userRequest.getUsername())
            );
        }

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailIsAlreadyTakenException(
                    String.format("Email %s is already taken.", userRequest.getEmail())
            );
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username {} to the database.", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public void sendSuccessfulRegistrationKafkaMessage(User user) { // ! Outbox pattern is missing
        try {
            String key = String.format("user-registered-%s", user.getId());
            String value = objectMapper.writeValueAsString(new UserRegisteredDTO(user.getId(), user.getUsername(), user.getEmail()));

            registerEmailKafkaTemplate
                    .send(USER_REGISTERED_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send UserRegisteredDTO to topic {}.", USER_REGISTERED_TOPIC_NAME, ex);

                        } else {
                            log.info("Success sending UserRegisteredDTO to topic {}.", USER_REGISTERED_TOPIC_NAME);
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("An error occurred with \"objectMapper.writeValueAsString(new UserRegisteredDTO(user.getId(), user.getUsername(), user.getEmail()))\".");
        }
    }

    @Override
    public AlterUserResponseDTO resetPassword(String userId, ResetPasswordRequestDTO requestDTO) {
        User user = getById(userId);

        if (!passwordEncoder.matches(requestDTO.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid old password.");
        }

        log.info("Resetting password for user with username {}.", user.getUsername());

        user.setPassword(encodePassword(requestDTO.getNewPassword()));
        user = userRepository.save(user);

        return new AlterUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
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

    private User getById(String id) {
        return userRepository
                .findById(id)
                .orElseThrow((() -> new NoSuchUserException(String.format("No user found with id %s.", id))));
    }

    @Override
    public AlterUserResponseDTO updateFields(String userId, PatchUserRequestDTO requestDTO) {
        User user = getById(userId);

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

        if (requestDTO.getUsername() != null || requestDTO.getEmail() != null) {
            log.info("Updating user with username {}.", user.getUsername());
            save(user);
        }

        /* if (requestDTO.getWalletAddress() != null) {
            paymentService.updateCryptoWalletAddress(userId, requestDTO.getWalletAddress());
        }
        */

        return new AlterUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
