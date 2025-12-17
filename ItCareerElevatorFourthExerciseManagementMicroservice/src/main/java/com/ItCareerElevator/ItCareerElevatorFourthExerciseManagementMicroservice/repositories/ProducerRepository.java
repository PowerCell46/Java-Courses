package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.utils.SoftDeleteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerRepository extends SoftDeleteRepository<Producer, String> {

    Optional<Producer> findByNameAndIsDeletedIsFalse(String name);
}
