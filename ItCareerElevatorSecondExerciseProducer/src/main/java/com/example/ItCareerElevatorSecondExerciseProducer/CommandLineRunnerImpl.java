package com.example.ItCareerElevatorSecondExerciseProducer;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiDocumentType;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.LoiMeasurementUnit;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.ElectricityInvoiceRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.LoiDocumentTypeRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.LoiMeasurementUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Order(2)
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ElectricityInvoiceRepository electricityInvoiceRepository;
    private final LoiDocumentTypeRepository loiDocumentTypeRepository;
    private final LoiMeasurementUnitRepository loiMeasurementUnitRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Message from CommandLineRunner...!");

        ElectricityInvoice electricityInvoice = new ElectricityInvoice(
                "BG2004235341",
                "911",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
                new BigDecimal("1.421"),
                new BigDecimal("15.381"),
                new BigDecimal("1245.128"),
                loiDocumentTypeRepository.findByListOptionItemCode(LoiDocumentType.INVOICE).orElse(null),
                loiMeasurementUnitRepository.findByListOptionItemCode(LoiMeasurementUnit.MWh).orElse(null)
        );

        electricityInvoice = electricityInvoiceRepository.save(electricityInvoice);

        System.out.println("SnowflakeId: " + electricityInvoice.getSnowflakeId());
    }
}
