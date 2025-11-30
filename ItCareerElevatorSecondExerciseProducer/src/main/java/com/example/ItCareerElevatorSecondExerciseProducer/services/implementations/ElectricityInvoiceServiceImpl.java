package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceResponseDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.ElectricityInvoiceRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElectricityInvoiceServiceImpl implements ElectricityInvoiceService {

    private final ElectricityInvoiceRepository electricityInvoiceRepository;

    @Override
    public ElectricityInvoiceResponseDTO processElectricityInvoice(CreateElectricityInvoiceRequestDTO requestDTO) {
        return new ElectricityInvoiceResponseDTO();
    }
}
