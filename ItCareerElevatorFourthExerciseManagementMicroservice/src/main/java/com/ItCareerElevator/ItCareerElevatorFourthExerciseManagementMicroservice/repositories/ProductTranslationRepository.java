package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, String> {

    Set<ProductTranslation> findAllByIsDeletedIsFalse();
}
