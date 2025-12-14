package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequestDTO {

    private Collection<LocaleRequestDTO> nameLocales;

    private Collection<LocaleRequestDTO> descriptionLocales;

    private String producerName;

    private BigDecimal price;

    private Integer inStockQuantity;
}
