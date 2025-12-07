package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.entities.City;

public interface CityService {

    City getOrCreateByName(String name);

    City save(City city);
}
