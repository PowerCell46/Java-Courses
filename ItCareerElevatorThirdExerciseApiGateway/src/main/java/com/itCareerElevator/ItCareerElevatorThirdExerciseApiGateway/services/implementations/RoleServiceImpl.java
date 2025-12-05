package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.Role;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.repositories.RoleRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.RoleService;
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
        return roleRepository.findByName(name);
    }

    @Override
    public Collection<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        log.info("Persisting role with name '{}' to the Database.", role.getName());

        return roleRepository.save(role);
    }
}
