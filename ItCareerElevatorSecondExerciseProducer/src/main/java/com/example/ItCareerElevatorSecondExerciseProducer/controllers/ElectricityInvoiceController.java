package com.example.ItCareerElevatorSecondExerciseProducer.controllers;

import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.CreateElectricityInvoiceRequestDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.DTOs.ElectricityInvoiceDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.services.interfaces.ElectricityInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/electricity-invoice")
@RequiredArgsConstructor
@Slf4j
public class ElectricityInvoiceController {

    private final ElectricityInvoiceService electricityInvoiceService;

    @PostMapping(value = "")
    public ResponseEntity<ElectricityInvoiceDTO> processElectricityInvoice(
            @Valid @RequestBody CreateElectricityInvoiceRequestDTO requestDTO
    ) {
        log.info("--- POST request /api/electricity-invoice:{}{}", System.lineSeparator(), requestDTO);

        ElectricityInvoiceDTO responseDTO = electricityInvoiceService.processElectricityInvoice(requestDTO);

        URI location = URI.create(String.format("/api/electricity-invoice/%s", responseDTO.getSnowflakeId()));

        return ResponseEntity.created(location).body(responseDTO);
    }
}
