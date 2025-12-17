package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequestDTO {

    @Valid
    @Size(min = 1, message = "NameLocales must contain at least one locale.")
    private List<LocaleRequestDTO> nameLocales;

    @Valid
    @Size(min = 1, message = "DescriptionLocales must contain at least one locale.")
    private List<LocaleRequestDTO> descriptionLocales;

    @Size(min = 2, max = 30, message = "ProducerName length must be between 2 and 30 characters.")
    private String producerName;

    @Positive(message = "Price must be greater than 0.")
    private BigDecimal price;

    @PositiveOrZero(message = "InStockQuantity must be greater than or equal to 0.")
    private Integer inStockQuantity;
}
