package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.repositories.OrderRepository;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void sendPdfInvoiceThroughEmail(String id) {
        log.info("Starting the generation process of the PDF.");

    }
}
