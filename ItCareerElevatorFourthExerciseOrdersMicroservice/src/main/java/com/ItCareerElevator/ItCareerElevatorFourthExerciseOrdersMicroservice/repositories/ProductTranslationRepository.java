package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, String> {

    Set<ProductTranslation> findAllByIsDeletedIsFalse();

    Optional<ProductTranslation> findByProductAndLocaleCodeAndIsDeletedIsFalse(Product product, String locale_code);
}
