package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.configs;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.utils.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.repositories",
        repositoryBaseClass = SoftDeleteRepositoryImpl.class
)
public class JpaConfig {
}
