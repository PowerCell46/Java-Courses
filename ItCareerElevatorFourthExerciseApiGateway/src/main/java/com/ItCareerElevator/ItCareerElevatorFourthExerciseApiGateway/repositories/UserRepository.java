package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.User;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, String> {

    Optional<User> findByUsernameAndIsDeletedIsFalse(String username);

    Optional<User> findByUsername(String username);
}
