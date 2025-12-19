package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.services.interfaces;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.Order;

public interface OrderService {

    void sendPdfInvoiceThroughEmail(String id);

    Order getById(String id);
}
