package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;

public interface ProducerService {

    Producer getOrCreateByName(String name);

    Producer save(Producer producer);
}
