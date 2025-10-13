package com.springweb;

import com.springweb.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
    }
}
