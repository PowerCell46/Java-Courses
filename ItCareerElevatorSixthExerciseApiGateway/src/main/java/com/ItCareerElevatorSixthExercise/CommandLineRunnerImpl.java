package com.ItCareerElevatorSixthExercise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("---> Message from CommandLineRunnerImpl...!");
    }
}
