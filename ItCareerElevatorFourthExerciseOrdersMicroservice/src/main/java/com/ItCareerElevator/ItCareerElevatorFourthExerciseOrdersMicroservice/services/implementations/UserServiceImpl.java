package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.User;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.exceptions.NoSuchUserException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.UserRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(String id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with id %s.", id)));
    }
}
