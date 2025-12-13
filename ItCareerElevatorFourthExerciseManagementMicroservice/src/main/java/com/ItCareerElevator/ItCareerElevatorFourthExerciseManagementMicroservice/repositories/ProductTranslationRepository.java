package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, String> {

    Set<ProductTranslation> findAllByIsDeletedIsFalse();

    Optional<ProductTranslation> findByProductAndLocaleCodeAndIsDeletedIsFalse(Product product, String locale_code);
}
