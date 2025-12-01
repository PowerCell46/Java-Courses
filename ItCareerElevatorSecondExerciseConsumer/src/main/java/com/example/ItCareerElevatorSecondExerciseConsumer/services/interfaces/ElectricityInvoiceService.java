package com.example.ItCareerElevatorSecondExerciseConsumer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseConsumer.DTOs.ElectricityInvoiceDTO;

public interface ElectricityInvoiceService {

    void sendPdfInvoiceThroughEmail(ElectricityInvoiceDTO electricityInvoiceDTO);
}
