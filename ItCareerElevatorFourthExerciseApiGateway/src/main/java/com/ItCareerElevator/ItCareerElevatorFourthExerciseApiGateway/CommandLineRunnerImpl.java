package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2) // * Defines the sort order for an annotated bean when Spring is dealing with multiple beans of the same type
// * Spring Boot will look at all beans that implement CommandLineRunner, sort them by @Order (lower value runs first), and then call run(...) in that order.
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---> Message from CommandLineRunner...");
    }
}
