package com.example.ItCareerElevatorSecondExerciseProducer;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.ElectricityInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ElectricityInvoiceRepository electricityInvoiceRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Message from CommandLineRunner...!");

        ElectricityInvoice electricityInvoice = new ElectricityInvoice(
                "BG2004235341",
                "911",
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 30),
                new BigDecimal("15.381"),
                new BigDecimal("1245.128")
        );

        electricityInvoiceRepository.save(electricityInvoice);
    }
}
