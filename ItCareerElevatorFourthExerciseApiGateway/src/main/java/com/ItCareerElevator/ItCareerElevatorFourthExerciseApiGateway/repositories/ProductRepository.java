package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByIdAndIsDeletedIsFalse(String id);

    Set<Product> findAllByIsDeletedIsFalse();

    Set<Product> findAllByTranslationsNameAndIsDeletedIsFalse(String translatedName);

    Page<Product> findAllByIsDeletedIsFalse(Pageable pageable);
}
