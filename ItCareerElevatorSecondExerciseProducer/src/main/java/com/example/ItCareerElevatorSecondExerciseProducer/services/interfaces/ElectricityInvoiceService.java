package com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.entities.ElectricityInvoice;

public interface ElectricityInvoiceService {

    ElectricityInvoiceDTO processElectricityInvoice(CreateElectricityInvoiceRequestDTO requestDTO);

    ElectricityInvoice save(ElectricityInvoice electricityInvoice);
}
