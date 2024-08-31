package com.mobilele;

import com.mobilele.services.interfaces.RolesService;
import com.mobilele.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final RolesService rolesService;
    private final UserService userService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello SPRING!");

        rolesService.seedRoles();
        userService.seedUsers();
    }
}
