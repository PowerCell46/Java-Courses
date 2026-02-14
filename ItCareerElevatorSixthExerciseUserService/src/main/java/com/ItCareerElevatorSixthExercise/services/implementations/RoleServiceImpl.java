package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.entities.Role;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchRoleException;
import com.ItCareerElevatorSixthExercise.repositories.RoleRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new NoSuchRoleException(String.format("No role found with name %s.", name)));
    }
}
