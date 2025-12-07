package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated.AuthRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.Role;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.RoleService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(1)
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void run(String... args) {
        Role adminRole = roleService
                .findByName("ROLE_ADMIN")
                .orElseGet(() -> roleService.save(new Role("ROLE_ADMIN")));

        Role managerRole = roleService
                .findByName("ROLE_MANAGER")
                .orElseGet(() -> roleService.save(new Role("ROLE_MANAGER")));

        final String ADMIN_USERNAME = "PowerCell46";
        final String ADMIN_PASSWORD = "JsonobJeCT51";

        if (userService.findByUsername(ADMIN_USERNAME).isEmpty()) {
            userService.register(new AuthRequestDTO(ADMIN_USERNAME, ADMIN_PASSWORD));

            User adminUser = userService.findByUsername(ADMIN_USERNAME).get();
            adminUser.setRoles(Set.of(adminRole, managerRole));
            adminUser = userService.save(adminUser);
        }
    }
}
