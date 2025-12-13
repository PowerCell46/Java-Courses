package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Locale;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Producer;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Product;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.ProductTranslation;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.LocaleRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.ProducerRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.ProductRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.repositories.ProductTranslationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseLoader implements CommandLineRunner {

    private final LocaleRepository localeRepository;
    private final ProductRepository productRepository;
    private final ProductTranslationRepository productTranslationRepository;
    private final ProducerRepository producerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (localeRepository.findAllByIsDeletedIsFalse().isEmpty())
            seedLocales();

        if (productRepository.findAllByIsDeletedIsFalse().isEmpty())
            seedProducts();
    }

    private void seedLocales() {
        Locale bulgarian = new Locale("bg");
        Locale english = new Locale("en");
        Locale spanish = new Locale("es");
        Locale german = new Locale("de");

        log.info("Persisting locales to the databases.");
        localeRepository.saveAll(Set.of(bulgarian, english, spanish, german));
    }

    private void seedProducts() {
        Producer gymBeamProducer = new Producer("Gym Beam");
        gymBeamProducer = producerRepository.save(gymBeamProducer);

        Product gymBeamProtein = new Product(
                BigDecimal.valueOf(77.32),
                17,
                gymBeamProducer
        );
        gymBeamProtein = productRepository.save(gymBeamProtein);

        ProductTranslation gymBeamProteinBgTranslation = new ProductTranslation(
                gymBeamProtein,
                localeRepository.findByCodeAndIsDeletedIsFalse("bg").get(),
                "джийм Бийм протеин",
                "Mногокомпонентен протеин, съдържащ суроватъчен протеинов концентрат (WPC) и казеин."
        );
        ProductTranslation gymBeamProteinEnTranslation = new ProductTranslation(
                gymBeamProtein,
                localeRepository.findByCodeAndIsDeletedIsFalse("en").get(),
                "GymBeam Protein",
                "A multi-component protein containing whey protein concentrate (WPC) and casein."
        );
        ProductTranslation gymBeamProteinEsTranslation = new ProductTranslation(
                gymBeamProtein,
                localeRepository.findByCodeAndIsDeletedIsFalse("es").get(),
                "Proteína GymBeam",
                "Proteína multicomponente que contiene concentrado de proteína de suero (WPC) y caseína."
        );

        var gymBeamProteinTranslations = Set.of(
                gymBeamProteinBgTranslation,
                gymBeamProteinEnTranslation,
                gymBeamProteinEsTranslation
        );
        productTranslationRepository.saveAll(gymBeamProteinTranslations);
    }
}
