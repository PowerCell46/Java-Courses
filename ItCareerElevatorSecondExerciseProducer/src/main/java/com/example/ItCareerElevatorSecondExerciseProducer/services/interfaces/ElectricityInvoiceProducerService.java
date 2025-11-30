package com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceResponseDTO;

public interface ElectricityInvoiceProducerService {

    void send(ElectricityInvoiceResponseDTO electricityInvoice);
}
