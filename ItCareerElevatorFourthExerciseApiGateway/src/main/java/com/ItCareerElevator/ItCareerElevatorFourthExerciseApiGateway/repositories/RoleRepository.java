package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.Role;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends SoftDeleteRepository<Role, String> {

    Optional<Role> findByNameAndIsDeletedIsFalse(String name);
}
