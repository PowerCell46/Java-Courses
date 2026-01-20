package com.ItCareerElevatorSixthExercise.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequestDTO {

    private Collection<TranslationFieldRequestDTO> nameTranslations;

    private Collection<TranslationFieldRequestDTO> descriptionTranslations;

    private String manufacturerName;

    private BigDecimal price;
}
