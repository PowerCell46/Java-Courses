package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends SoftDeleteRepository<Producer, String> {

    Optional<Producer> findByNameAndIsDeletedIsFalse(String name);
}
