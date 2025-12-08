package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.exceptions.NoSuchUserException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.repositories.UserRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getBySnowflakeId(String snowflakeId) {
        return userRepository
                .findById(User.convertSnowflakeIdToId(snowflakeId))
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with id %s.", snowflakeId)));
    }
}
