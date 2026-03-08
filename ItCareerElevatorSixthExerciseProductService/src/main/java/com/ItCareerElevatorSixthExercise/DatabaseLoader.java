package com.ItCareerElevatorSixthExercise;

import com.ItCareerElevatorSixthExercise.entities.product.Locale;
import com.ItCareerElevatorSixthExercise.repositories.product.LocaleRepository;
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
    public void run(String... args) {
        if (localeRepository.findAll().isEmpty()) {
            seedLocales();
        }
    }

    private void seedLocales() {
        Locale english = new Locale(Locale.DEFAULT_LANGUAGE_CODE, Locale.DEFAULT_LANGUAGE_NAME);
        Locale bulgarian = new Locale("bg", "Bulgarian");
        Locale spanish = new Locale("es", "Spanish");
        Locale german = new Locale("de", "German");

        Set<Locale> locales = Set.of(bulgarian, english, spanish, german);

        log.info("Persisting {} locales to the database.", locales.size());
        localeRepository.saveAll(locales);
    }
}
