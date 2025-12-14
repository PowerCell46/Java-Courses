package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateProductRequestDTO {

    private List<LocaleRequestDTO> nameLocales; // required

    private List<LocaleRequestDTO> descriptionLocales; // required

    private String producerName; // required

    private BigDecimal price; // required

    private Integer inStockQuantity; // * optional
}
