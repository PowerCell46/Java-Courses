package com.ItCareerElevatorSixthExercise.DTOs.product.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
    @Size(min = 1, message = "Name translations must contain at least one locale.")
    private List<TranslationFieldRequestDTO> nameTranslations;

    @Valid
    @Size(min = 1, message = "Description translations must contain at least one locale.")
    private List<TranslationFieldRequestDTO> descriptionTranslations;

    @Size(min = 2, max = 30, message = "Manufacturer length must be between 2 and 30 characters.")
    private String manufacturerName;

    @Positive(message = "Price must be greater than 0.")
    private BigDecimal price;
}
