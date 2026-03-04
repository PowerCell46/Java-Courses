package com.ItCareerElevatorSixthExercise.services.implementations.product;

import com.ItCareerElevatorSixthExercise.entities.product.Manufacturer;
import com.ItCareerElevatorSixthExercise.repositories.product.ManufacturerRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ManufacturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer getOrCreateByName(String name) {
        return manufacturerRepository
                .findByName(name)
                .orElseGet(() -> save(new Manufacturer(name)));
    }

    @Override
    public Manufacturer save(Manufacturer producer) {
        log.info("Persisting manufacturer with name {} to the database.", producer.getName());

        return manufacturerRepository.save(producer);
    }
}
