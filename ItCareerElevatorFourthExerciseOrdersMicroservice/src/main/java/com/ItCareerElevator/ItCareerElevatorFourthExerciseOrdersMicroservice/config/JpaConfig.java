package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.config;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories",
        repositoryBaseClass = SoftDeleteRepositoryImpl.class
)
public class JpaConfig {
}
