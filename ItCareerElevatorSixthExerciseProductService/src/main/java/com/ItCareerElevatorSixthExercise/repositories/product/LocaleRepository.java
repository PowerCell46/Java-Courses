package com.ItCareerElevatorSixthExercise.repositories.product;

import com.ItCareerElevatorSixthExercise.entities.product.Locale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocaleRepository extends JpaRepository<Locale, String> {

    Optional<Locale> findByCode(String code);
}
