package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.CreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.FollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.UpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.CommonEntity;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated.NoSuchUserException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated.UserCannotFollowThemselvesException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated.UserNotFollowingException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.repositories.UserRepository;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.CityService;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.CountryService;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final CityService cityService;
    private final CountryService countryService;

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO create(CreateUserRequestDTO requestDTO) {
        User user = constructNonPersistedUser(requestDTO);
        user = save(user);

        return constructUserResponseDTO(user);
    }

    private User constructNonPersistedUser(CreateUserRequestDTO requestDTO) {
        User nonPersistedUser = new User(requestDTO.getUsername());

        // * Manually set the id, not letting the @PrePersist assign a new one (reuse the request one)
        nonPersistedUser
                .setId(CommonEntity.convertSnowflakeIdToId(requestDTO.getSnowflakeId()));

        return nonPersistedUser;
    }

    private UserResponseDTO constructUserResponseDTO(User user) {
        return UserResponseDTO
                .builder()
                .snowflakeId(user.getSnowflakeId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isMale(user.getIsMale())
                .bio(user.getBio())
                .city(user.getCity() != null ? user.getCity().getName() : null)
                .country(user.getCountry() != null ? user.getCountry().getName() : null)
                .following(user.getFollowing().stream().map(User::getUsername).toList())
                .build();
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
    public User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with username %s.", username)));
    }

    @Override
    public UserResponseDTO updateUser(UpdateUserRequestDTO requestDTO) {
        User user = getBySnowflakeId(requestDTO.getSnowflakeId());

        if (requestDTO.getFirstName() != null) {
            user.setFirstName(requestDTO.getFirstName().strip());
        }
        if (requestDTO.getLastName() != null) {
            user.setLastName(requestDTO.getLastName().strip());
        }
        if (requestDTO.getIsMale() != null) {
            user.setIsMale(requestDTO.getIsMale());
        }
        if (requestDTO.getBio() != null) {
            user.setBio(requestDTO.getBio().strip());
        }
        if (requestDTO.getCity() != null) {
            user.setCity(cityService.getOrCreateByName(requestDTO.getCity().strip()));
        }
        if (requestDTO.getCountry() != null) {
            user.setCountry(countryService.getOrCreateByName(requestDTO.getCountry().strip()));
        }

        if (
            // @formatter:off
                requestDTO.getFirstName() != null || requestDTO.getLastName() != null ||
                requestDTO.getIsMale() != null || requestDTO.getBio() != null ||
                requestDTO.getCity() != null || requestDTO.getCountry() != null
            // @formatter:on
        ) {
            user = save(user);
        }

        return constructUserResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO follow(FollowUserRequestDTO requestDTO) {
        User follower = getBySnowflakeId(requestDTO.getFollowerId()); // Current logged-in user
        User followed = getByUsername(requestDTO.getFollowedUsername());

        if (follower.equals(followed)) {
            throw new UserCannotFollowThemselvesException("User cannot follow themselves.");
        }

        follower.getFollowing().add(followed);
        followed.getFollowers().add(follower);

        follower = save(follower);

        return constructUserResponseDTO(follower);
    }

    @Override
    @Transactional
    public UserResponseDTO unfollow(FollowUserRequestDTO requestDTO) {
        User unfollower = getBySnowflakeId(requestDTO.getFollowerId()); // Current logged-in user
        User unfollowed = getByUsername(requestDTO.getFollowedUsername());

        boolean removed = unfollower.getFollowing().remove(unfollowed);
        if (!removed) {
            throw new UserNotFollowingException(
                    String.format(
                            "User %s is not following %s.",
                            unfollower.getUsername(),
                            unfollowed.getUsername()
                    )
            );
        }

        unfollowed.getFollowers().remove(unfollower);

        unfollower = save(unfollower);
        save(unfollowed);

        return constructUserResponseDTO(unfollower);
    }
}
