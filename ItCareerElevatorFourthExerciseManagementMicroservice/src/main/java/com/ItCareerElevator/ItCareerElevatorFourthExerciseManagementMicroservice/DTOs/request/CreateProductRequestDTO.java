package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateProductRequestDTO {

    private List<LocaleRequestDTO> nameLocales;

    private List<LocaleRequestDTO> descriptionLocales;

    private String producerName;

    private BigDecimal price;

    private Integer inStockQuantity;
}
