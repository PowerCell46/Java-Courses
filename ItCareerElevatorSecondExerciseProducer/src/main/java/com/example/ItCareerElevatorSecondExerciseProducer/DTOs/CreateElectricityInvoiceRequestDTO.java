package com.example.ItCareerElevatorSecondExerciseProducer.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateElectricityInvoiceRequestDTO {

    private String accessPoint;

    private String iban;

    // TODO: recipientEmail

    // TODO: taxEventDate

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate periodFrom;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate periodTo;

    private BigDecimal quantity;

    private BigDecimal singlePrice;

    private Long loiDocumentTypeCode;

    private Long loiMeasurementUnitCode;
}

// TODO: VALIDATIONS TO THE FIELDS!