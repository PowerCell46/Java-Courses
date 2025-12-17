package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.config;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories",
        repositoryBaseClass = SoftDeleteRepositoryImpl.class
)
public class JpaConfig {
}
