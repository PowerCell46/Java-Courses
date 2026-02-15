package com.ItCareerElevatorSixthExercise;

import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;
import com.ItCareerElevatorSixthExercise.repositories.LoiOrderStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final LoiOrderStatusRepository orderStatusRepository;

    @Override
    public void run(String... args) throws Exception {
        if (orderStatusRepository.findAll().isEmpty()) {
            seedOrderStatuses();
        }
    }

    private void seedOrderStatuses() {
        orderStatusRepository.save(new LoiOrderStatus("FAILED", LoiOrderStatus.FAILED));
        orderStatusRepository.save(new LoiOrderStatus("CREATED", LoiOrderStatus.CREATED));
        orderStatusRepository.save(new LoiOrderStatus("CONFIRMED", LoiOrderStatus.CONFIRMED));
        orderStatusRepository.save(new LoiOrderStatus("CANCELLED", LoiOrderStatus.CANCELLED));
    }
}
