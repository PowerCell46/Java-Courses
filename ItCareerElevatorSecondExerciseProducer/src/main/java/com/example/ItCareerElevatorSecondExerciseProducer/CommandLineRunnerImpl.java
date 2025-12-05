package com.example.ItCareerElevatorSecondExerciseProducer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2) // ! Check whether for all components, or only for CommandLineRunner implementations
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Message from CommandLineRunner...!");
    }
}
