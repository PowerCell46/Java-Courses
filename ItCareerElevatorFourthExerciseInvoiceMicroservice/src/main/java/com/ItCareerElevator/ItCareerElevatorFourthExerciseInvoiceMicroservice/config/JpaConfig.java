package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.config;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.utils.SoftDeleteRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.repositories",
        repositoryBaseClass = SoftDeleteRepositoryImpl.class
)
public class JpaConfig {
}
