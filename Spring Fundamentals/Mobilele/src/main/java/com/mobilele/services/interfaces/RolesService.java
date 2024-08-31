package com.mobilele.services.interfaces;

import com.mobilele.models.entities.Role;

import java.util.Optional;

public interface RolesService {

    void seedRoles();

    Optional<Role> findById(long id);
}
