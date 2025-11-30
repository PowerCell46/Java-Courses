package com.example.ItCareerElevatorSecondExerciseProducer.services.implementations;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceResponseDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.exceptions.InvalidRelationshipException;
import com.example.ItCareerElevatorSecondExerciseProducer.repositories.ElectricityInvoiceRepository;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceService;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.LoiDocumentTypeService;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.LoiMeasurementUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElectricityInvoiceServiceImpl implements ElectricityInvoiceService {

    private final ElectricityInvoiceRepository electricityInvoiceRepository;
    private final LoiDocumentTypeService loiDocumentTypeService;
    private final LoiMeasurementUnitService loiMeasurementUnitService;

    @Override
    public ElectricityInvoiceResponseDTO processElectricityInvoice(CreateElectricityInvoiceRequestDTO requestDTO) {
        validateRelationships(requestDTO);

        return new ElectricityInvoiceResponseDTO();
    }

    private void validateRelationships(CreateElectricityInvoiceRequestDTO requestDTO) {
        if (loiDocumentTypeService.getByListOptionItemCode(requestDTO.getLoiDocumentTypeCode()).isEmpty()) {
            throw new InvalidRelationshipException("Invalid LoiDocumentTypeCode: " + requestDTO.getLoiDocumentTypeCode());
        }

        if (loiMeasurementUnitService.getByListOptionItemCode(requestDTO.getLoiMeasurementUnitCode()).isEmpty()) {
            throw new InvalidRelationshipException("Invalid LoiMeasurementUnitCode: " + requestDTO.getLoiMeasurementUnitCode());
        }
    }
}
