package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.City;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.repositories.CityRepository;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public City getOrCreateByName(String name) {
        return cityRepository
                .findByName(name)
                .orElseGet(() -> save(constructNonPersistedCity(name)));
    }

    private City constructNonPersistedCity(String name) {
        return new City(name);
    }

    @Override
    public City save(City city) {
        log.info("Saving city with name {} to the Database.", city.getName());

        return cityRepository.save(city);
    }
}
