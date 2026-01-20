package com.ItCareerElevatorSixthExercise.services.interfaces;


import com.ItCareerElevatorSixthExercise.entities.Locale;

public interface LocaleService {

    Locale getByCode(String code);

    Locale save(Locale locale);
}
