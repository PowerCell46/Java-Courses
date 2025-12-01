package com.example.ItCareerElevatorSecondExerciseConsumer.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString // * FOR TEST
@Builder
@AllArgsConstructor
public class ElectricityInvoiceDTO {

    @JsonProperty("id")
    private String snowflakeId;

    @JsonProperty("email")
    private String recipientEmail;

    private String accessPoint;

    private String invoiceNumber;

    private String iban;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate taxEventDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate periodFrom;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate periodTo;

    private BigDecimal quantity;

    private BigDecimal singlePrice;

    private BigDecimal totalSumWithoutVAT;

    @JsonProperty("VAT")
    private BigDecimal VAT;

    private String loiDocumentTypeName;

    private String loiMeasurementUnitName;
}
