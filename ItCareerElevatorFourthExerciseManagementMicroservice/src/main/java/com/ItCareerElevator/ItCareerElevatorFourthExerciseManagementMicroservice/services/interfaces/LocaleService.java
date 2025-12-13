package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Locale;

public interface LocaleService {

    Locale getByCode(String code);
}
