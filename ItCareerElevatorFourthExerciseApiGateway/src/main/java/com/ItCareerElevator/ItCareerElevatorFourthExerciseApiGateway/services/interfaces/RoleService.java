package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.Role;
import java.util.Collection;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(String name);

    Collection<Role> getAll();

    Role save(Role role);
}
