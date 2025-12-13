package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Locale;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.exceptions.NoSuchLocaleException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.LocaleRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.services.interfaces.LocaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {

    private final LocaleRepository localeRepository;

    @Override
    public Locale getByCode(String code) {
        return localeRepository
                .findByCodeAndIsDeletedIsFalse(code)
                .orElseThrow(() -> new NoSuchLocaleException(String.format("Invalid locale %s.", code)));
    }
}
