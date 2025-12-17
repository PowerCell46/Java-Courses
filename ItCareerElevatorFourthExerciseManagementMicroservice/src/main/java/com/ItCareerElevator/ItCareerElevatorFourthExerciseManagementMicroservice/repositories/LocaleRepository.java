package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Locale;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocaleRepository extends SoftDeleteRepository<Locale, String> {

    Optional<Locale> findByCodeAndIsDeletedIsFalse(String code);
}
