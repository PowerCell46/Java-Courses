package com.example.ItCareerElevatorSecondExerciseProducer;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiDocumentType;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiMeasurementUnit;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.LoiDocumentTypeRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.LoiMeasurementUnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final LoiDocumentTypeRepository loiDocumentTypeRepository;
    private final LoiMeasurementUnitRepository loiMeasurementUnitRepository;

    @Override
    public void run(String... args) throws Exception {
        seedLoiDocumentTypes();
        seedLoiMeasurementUnits();

        log.info("DatabaseLoader finished its work.");
    }

    private void seedLoiDocumentTypes() {
        if (loiDocumentTypeRepository.findAll().isEmpty()) {
            LoiDocumentType invoice = new LoiDocumentType("Фактура", LoiDocumentType.INVOICE);
            LoiDocumentType debitNote = new LoiDocumentType("Дебитно известие", LoiDocumentType.DEBIT_NOTE);
            LoiDocumentType creditNote = new LoiDocumentType("Кредитно известие", LoiDocumentType.CREDIT_NOTE);

            loiDocumentTypeRepository.saveAll(List.of(invoice, debitNote, creditNote));
            log.debug("Seeded LoiDocumentType entries.");
        }
    }

    private void seedLoiMeasurementUnits() {
        if (loiMeasurementUnitRepository.findAll().isEmpty()) {
            LoiMeasurementUnit Mwh = new LoiMeasurementUnit("MWh", LoiMeasurementUnit.MWh);
            LoiMeasurementUnit KWh = new LoiMeasurementUnit("KWh", LoiMeasurementUnit.KWh);

            loiMeasurementUnitRepository.saveAll(List.of(Mwh, KWh));
            log.debug("Seeded LoiMeasurementUnit entries.");
        }
    }
}
