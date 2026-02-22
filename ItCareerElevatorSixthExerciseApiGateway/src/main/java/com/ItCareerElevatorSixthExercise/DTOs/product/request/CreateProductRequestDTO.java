package com.ItCareerElevatorSixthExercise.DTOs.product.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class CreateProductRequestDTO {

    @Valid
    @NotNull(message = "Name translations must not be null.")
    @Size(min = 1, message = "Name translations must contain at least one entry.")
    private List<TranslationFieldRequestDTO> nameTranslations;

    @Valid
    @NotNull(message = "Description translations must not be null.")
    @Size(min = 1, message = "Description translations must contain at least one entry.")
    private List<TranslationFieldRequestDTO> descriptionTranslations;

    @NotNull(message = "Manufacturer must not be null.")
    @Size(min = 2, max = 30, message = "Manufacturer length must be between 2 and 30 characters.")
    private String manufacturerName;

    @NotNull(message = "Price must not be null.")
    @Positive(message = "Price must be greater than 0.")
    private BigDecimal price;

    @NotNull(message = "In stock quantity must not be null.")
    @PositiveOrZero(message = "In stock quantity must be greater than or equal to 0.")
    private Integer inStockQuantity;
}
