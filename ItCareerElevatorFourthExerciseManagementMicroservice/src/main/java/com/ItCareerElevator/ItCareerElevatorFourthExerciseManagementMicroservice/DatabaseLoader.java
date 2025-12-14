package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.entities.Locale;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.repositories.LocaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final LocaleRepository localeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (localeRepository.findAllByIsDeletedIsFalse().isEmpty())
            seedLocales();
    }

    private void seedLocales() {
        Locale bulgarian = new Locale("bg");
        Locale english = new Locale("en");
        Locale spanish = new Locale("es");
        Locale german = new Locale("de");

        Set<Locale> locales = Set.of(bulgarian, english, spanish, german);

        log.info("Persisting {} locales to the database.", locales.size());
        localeRepository.saveAll(locales);
    }
}
