package com.mobilele.services.impls;

import com.mobilele.models.entities.Role;
import com.mobilele.models.enums.Roles;
import com.mobilele.repositories.RolesRepository;
import com.mobilele.services.interfaces.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesRepository rolesRepository;

    @Override
    public void seedRoles() {
        if (rolesRepository.count() > 0) return;

        List<Role> initRoles = List.of(
            Role
                .builder()
                .name(Roles.ADMIN)
                .build(),

            Role
                .builder()
                .name(Roles.USER)
                .build()
        );

        rolesRepository.saveAllAndFlush(initRoles);
    }

    @Override
    public Optional<Role> findById(long id) {
        return rolesRepository.findById(id);
    }
}
