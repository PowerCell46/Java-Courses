package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequestDTO {

    private Collection<LocaleRequestDTO> nameLocales; // optional

    private Collection<LocaleRequestDTO> descriptionLocales; // optional

    private String producerName; // optional

    private BigDecimal price; // optional

    private Integer inStockQuantity; // optional
}
