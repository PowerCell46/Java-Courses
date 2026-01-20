package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.entities.Locale;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchLocaleException;
import com.ItCareerElevatorSixthExercise.repositories.LocaleRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.LocaleService;
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
                .findByCode(code)
                .orElseThrow(() ->
                        new NoSuchLocaleException(String.format("Invalid or non-existing locale %s.", code))
                );
    }

    @Override
    public Locale save(Locale locale) {
        log.info("Persisting locale with code {} to the database.", locale.getCode());

        return localeRepository.save(locale);
    }
}
