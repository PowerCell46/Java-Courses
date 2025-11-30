package com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceResponseDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;

public interface ElectricityInvoiceService {

    ElectricityInvoiceResponseDTO processElectricityInvoice(CreateElectricityInvoiceRequestDTO requestDTO);

    ElectricityInvoice save(ElectricityInvoice electricityInvoice);
}
