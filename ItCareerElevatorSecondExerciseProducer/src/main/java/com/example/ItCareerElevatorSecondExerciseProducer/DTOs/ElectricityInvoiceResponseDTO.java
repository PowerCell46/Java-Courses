package com.example.ItCareerElevatorSecondExerciseProducer.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ElectricityInvoiceResponseDTO {

    private String accessPoint;

    private String invoiceNumber;

    private String iban;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate periodFrom;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate periodTo;

    private BigDecimal quantity;

    private BigDecimal singlePrice;

    private BigDecimal totalSumWithoutVAT;

    private BigDecimal VAT;

    private String loiDocumentTypeName;

    private String loiMeasurementUnitName;
}
