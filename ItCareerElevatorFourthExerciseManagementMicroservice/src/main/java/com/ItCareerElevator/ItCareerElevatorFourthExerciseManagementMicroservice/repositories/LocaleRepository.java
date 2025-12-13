package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Locale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface LocaleRepository extends JpaRepository<Locale, String> {

    Set<Locale> findAllByIsDeletedIsFalse();

    Optional<Locale> findByCodeAndIsDeletedIsFalse(String code);
}
