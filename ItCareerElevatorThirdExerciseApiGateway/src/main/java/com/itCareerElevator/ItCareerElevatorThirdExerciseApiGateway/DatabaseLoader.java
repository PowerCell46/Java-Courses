package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.Role;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.RoleService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(1)
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleService
                .findByName("ROLE_ADMIN")
                .orElseGet(() -> roleService.save(new Role("ROLE_ADMIN")));

        Role userRole = roleService
                .findByName("ROLE_USER")
                .orElseGet(() -> roleService.save(new Role("ROLE_USER")));

        final String ADMIN_USERNAME = "PowerCell46";

        if (userService.findByUsername(ADMIN_USERNAME).isEmpty()) {
            User admin = new User(
                    ADMIN_USERNAME,
                    encoder.encode("JsonobJeCT51"),
                    Set.of(adminRole, userRole)
            );
            userService.save(admin);
        }
    }
}
