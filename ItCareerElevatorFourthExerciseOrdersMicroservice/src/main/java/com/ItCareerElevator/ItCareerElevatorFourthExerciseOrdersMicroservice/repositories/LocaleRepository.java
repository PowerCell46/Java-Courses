package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Locale;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocaleRepository extends SoftDeleteRepository<Locale, String> {

    Optional<Locale> findByCodeAndIsDeletedIsFalse(String code);
}
