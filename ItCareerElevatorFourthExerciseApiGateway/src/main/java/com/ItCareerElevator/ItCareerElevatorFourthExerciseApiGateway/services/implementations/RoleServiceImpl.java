package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.Role;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.repositories.RoleRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByNameAndIsDeletedIsFalse(name);
    }

    @Override
    public Collection<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        log.info("Persisting role with name {} to the Database.", role.getName());

        return roleRepository.save(role);
    }
}
