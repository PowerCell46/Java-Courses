package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.repositories.UserRepository;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO requestDTO) {
        User user = constructNonPersistedUser(requestDTO);
        user = save(user);

        return UserResponseDTO
                .builder()
                .id(user.getSnowflakeId())
                .username(user.getUsername())
                .build();
    }

    private User constructNonPersistedUser(CreateUserRequestDTO requestDTO) {
        User nonPersistedUser = new User(requestDTO.getUsername());

        // * Set manually the id, not letting the @PrePersist assign a new one (use the request one)
        nonPersistedUser
                .setId(CommonEntity.convertSnowflakeIdToId(requestDTO.getSnowflakeId()));

        return nonPersistedUser;
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username {} to the Database.", user.getUsername());

        return userRepository.save(user);
    }
}
