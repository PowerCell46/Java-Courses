package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProducerRepository extends SoftDeleteRepository<Producer, String> {

    Optional<Producer> findByNameAndIsDeletedIsFalse(String name);

    Set<Producer> findAllByIsDeletedIsFalse();
}
