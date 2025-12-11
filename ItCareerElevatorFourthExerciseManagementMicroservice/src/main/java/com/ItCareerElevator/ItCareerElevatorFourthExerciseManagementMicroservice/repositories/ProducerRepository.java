package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, String> {

    Optional<Producer> findByNameAndIsDeletedIsFalse(String name);

    Set<Producer> findAllByIsDeletedIsFalse();
}
