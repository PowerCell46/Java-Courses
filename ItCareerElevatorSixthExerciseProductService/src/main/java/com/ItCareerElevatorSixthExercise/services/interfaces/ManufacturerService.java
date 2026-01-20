package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.entities.Manufacturer;

public interface ManufacturerService {

    Manufacturer getOrCreateByName(String name);

    Manufacturer save(Manufacturer producer);
}
