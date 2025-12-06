package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.NoSuchUserException;
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
    public UserResponseDTO create(CreateUserRequestDTO requestDTO) {
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

        // * Manually set the id, not letting the @PrePersist assign a new one (reuse the request one)
        nonPersistedUser
                .setId(CommonEntity.convertSnowflakeIdToId(requestDTO.getSnowflakeId()));

        return nonPersistedUser;
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username {} to the Database.", user.getUsername());

        return userRepository.save(user);
    }

    @Override
    public User getBySnowflakeId(String snowflakeId) {
        return userRepository
                .findById(User.convertSnowflakeIdToId(snowflakeId))
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with id %s.", snowflakeId)));
    }

    @Override
    public UserResponseDTO follow(FollowUserRequestDTO requestDTO) {
        User follower = getBySnowflakeId(requestDTO.getFollowerId()); // Current logged-in user
        User followed = getBySnowflakeId(requestDTO.getFollowedId());

        followed.getFollowers().add(follower);
        followed = save(followed);

        return UserResponseDTO
                .builder()
                .id(followed.getSnowflakeId())
                .username(followed.getUsername())
                .followers(followed.getFollowers().stream().map(User::getUsername).toList())
                .build();
    }
}
