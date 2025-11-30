package com.example.ItCareerElevatorSecondExerciseProducer.controllers;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceResponseDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/electricity-invoice")
@RequiredArgsConstructor
@Slf4j
public class ElectricityInvoiceController {

    private final ElectricityInvoiceService electricityInvoiceService;

    @PostMapping(value = "")
    public ResponseEntity<ElectricityInvoiceResponseDTO> processElectricityInvoice(
            @Valid @RequestBody CreateElectricityInvoiceRequestDTO requestDTO
    ) {
        log.info("--- POST request /api/electricity-invoice:{}{}", System.lineSeparator(), requestDTO);

        ElectricityInvoiceResponseDTO responseDTO = electricityInvoiceService.processElectricityInvoice(requestDTO);

        // TODO: URL?!

        return ResponseEntity.created(null).body(responseDTO);
    }
}
