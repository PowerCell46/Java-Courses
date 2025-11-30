package com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceDTO;

public interface ElectricityInvoiceProducerService {

    void send(ElectricityInvoiceDTO electricityInvoice);
}
