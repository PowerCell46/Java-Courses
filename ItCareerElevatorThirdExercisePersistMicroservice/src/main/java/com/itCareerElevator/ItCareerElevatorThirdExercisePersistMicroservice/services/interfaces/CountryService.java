package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.Country;

public interface CountryService {

    Country getOrCreateByName(String name);

    Country save(Country country);
}
