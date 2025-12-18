package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTranslationRepository extends SoftDeleteRepository<ProductTranslation, String> {

    Optional<ProductTranslation> findByProductAndLocaleCodeAndIsDeletedIsFalse(Product product, String locale_code);
}
