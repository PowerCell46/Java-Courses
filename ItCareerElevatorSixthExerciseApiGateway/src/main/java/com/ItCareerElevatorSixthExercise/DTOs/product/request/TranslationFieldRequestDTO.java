package com.ItCareerElevatorSixthExercise.DTOs.product.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TranslationFieldRequestDTO {

    @NotNull(message = "Code must not be null.")
    @Size(max = 10, message = "Code must not be longer than 10 characters.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Code must contain letters only.")
    private String code;

    @NotNull(message = "Translation must not be null.")
    @Size(min = 1, max = 500, message = "Translation length must be between 1 and 500 characters.")
    private String translation;
}
