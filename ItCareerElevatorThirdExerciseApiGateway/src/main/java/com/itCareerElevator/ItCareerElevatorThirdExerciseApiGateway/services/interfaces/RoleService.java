package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.Role;

import java.util.Collection;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(String name);

    Collection<Role> getAll();

    Role save(Role role);
}
