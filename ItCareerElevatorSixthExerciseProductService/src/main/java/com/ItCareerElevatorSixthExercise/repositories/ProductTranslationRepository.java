package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.Product;
import com.ItCareerElevatorSixthExercise.entities.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Long> {

    List<ProductTranslation> findAllByName(String name);

     Optional<ProductTranslation> findByProductAndLocaleCode(Product product, String locale_code);
}
