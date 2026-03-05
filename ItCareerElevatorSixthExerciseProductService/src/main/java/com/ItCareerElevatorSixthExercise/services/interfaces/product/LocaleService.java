package com.ItCareerElevatorSixthExercise.services.interfaces.product;


import com.ItCareerElevatorSixthExercise.entities.product.Locale;

public interface LocaleService {

    Locale getByCode(String code);

    Locale save(Locale locale);
}
