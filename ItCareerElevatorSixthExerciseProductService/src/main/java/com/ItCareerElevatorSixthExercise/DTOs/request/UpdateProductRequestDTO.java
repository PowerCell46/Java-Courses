package com.ItCareerElevatorSixthExercise.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequestDTO {

    private List<TranslationFieldRequestDTO> nameTranslations;

    private List<TranslationFieldRequestDTO> descriptionTranslations;

    private String manufacturerName;

    private BigDecimal price;
}
