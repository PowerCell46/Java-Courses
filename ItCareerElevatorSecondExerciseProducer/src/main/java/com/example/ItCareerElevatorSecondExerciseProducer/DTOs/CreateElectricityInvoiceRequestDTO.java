package com.example.ItCareerElevatorSecondExerciseProducer.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateElectricityInvoiceRequestDTO {

    @NotNull(message = "Access point is required.")
    @Pattern(regexp = "^BG\\d{8}$", message = "Access point must start with 'BG' followed by 8 digits.")
    @Size(max = 10, min = 10, message = "Access point must be 10 characters long.")
    private String accessPoint;

    @JsonProperty("email")
    @NotNull(message = "Email is required.")
    @Email(message = "Email must be a valid address.")
    @Size(max = 254, message = "Email must not be longer than 254 characters.")
    private String recipientEmail;

    @NotNull(message = "Iban is required.")
    @Size(max = 22, min = 22, message = "IBAN must be 22 characters long.")
    @Pattern(regexp = "^BG[0-9A-Za-z]{20}$", message = "IBAN must start with 'BG' and contain only letters and digits.")
    private String iban;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Tax event date is required.")
    @Future(message = "Tax event date must be in the future.")
    private LocalDate taxEventDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Period from is required.")
    @Future(message = "Period from must be in the future.")
    private LocalDate periodFrom;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @NotNull(message = "Period to is required.")
    @Future(message = "Period to must be in the future.")
    private LocalDate periodTo;

    @NotNull(message = "Quantity is required.")
    @DecimalMin(value = "0.0001", message = "Quantity must be greater than 0.")
    private BigDecimal quantity;

    @NotNull(message = "Single price is required.")
    @DecimalMin(value = "0.0001", message = "Single price must be greater than 0.")
    private BigDecimal singlePrice;

    @NotNull(message = "Document type code is required.")
    @Min(value = 1, message = "Document type code must be greater than 0.")
    private Long loiDocumentTypeCode;

    @NotNull(message = "Measurement unit code is required.")
    @Min(value = 1, message = "Measurement unit code must be greater than 0.")
    private Long loiMeasurementUnitCode;
}
