package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.Order;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.exceptions.NoSuchOrderException;
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
    public void sendPdfInvoiceThroughEmail(String orderId) {
        log.info("Starting the generation process of the PDF.");

        Order order = getById(orderId);

    }

    @Override
    public Order getById(String id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchOrderException(String.format("No order found with id %s.", id)));
    }
}
