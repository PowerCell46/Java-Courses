package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends SoftDeleteRepository<Product, String> {

    Set<Product> findAllByTranslationsNameAndIsDeletedIsFalse(String translatedName);
}
