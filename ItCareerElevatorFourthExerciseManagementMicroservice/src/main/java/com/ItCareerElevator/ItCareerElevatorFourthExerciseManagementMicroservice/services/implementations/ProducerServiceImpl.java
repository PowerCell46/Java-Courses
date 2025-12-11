package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.ProducerRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;

    @Override
    public Producer getOrCreateByName(String name) {
        return producerRepository
                .findByName(name)
                .orElseGet(() -> {
                    Producer producer = constructNonPersistedProducer(name);
                    return save(producer);
                });
    }

    private Producer constructNonPersistedProducer(String name) {
        return new Producer(name);
    }

    @Override
    public Producer save(Producer producer) {
        log.info("Persisting producer with name {} to the database.", producer.getName());

        return producerRepository.save(producer);
    }
}
