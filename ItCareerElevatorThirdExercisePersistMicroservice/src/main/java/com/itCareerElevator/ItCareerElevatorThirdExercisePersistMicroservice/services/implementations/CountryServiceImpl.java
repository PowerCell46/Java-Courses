package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.Country;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.repositories.CountryRepository;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Country getOrCreateByName(String name) {
        return countryRepository
                .findByName(name)
                .orElseGet(() -> save(constructNonPersistedCountry(name)));
    }

    private Country constructNonPersistedCountry(String name) {
        return new Country(name);
    }

    @Override
    public Country save(Country country) {
        log.info("Saving country with name {} to the Database.", country.getName());

        return countryRepository.save(country);
    }
}
