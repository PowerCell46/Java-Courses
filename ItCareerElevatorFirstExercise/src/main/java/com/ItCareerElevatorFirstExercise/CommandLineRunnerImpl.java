package com.ItCareerElevatorFirstExercise;

import com.ItCareerElevatorFirstExercise.entities.UrlMapper;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UrlMapperService urlMapperService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Message from the CommandLineRunner...!");
    }
}
