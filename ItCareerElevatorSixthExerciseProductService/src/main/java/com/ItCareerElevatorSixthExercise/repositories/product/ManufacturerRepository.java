package com.ItCareerElevatorSixthExercise.repositories.product;

import com.ItCareerElevatorSixthExercise.entities.product.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, String> {

    Optional<Manufacturer> findByName(String name);
}
