package com.ItCareerElevatorFirstExercise.DTOs;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class UrlMapperCreateRequestDTO {

    @Pattern(
            regexp = "^(https?://).+",
            message = "URL must start with http:// or https://"
    )
    @Size(max = 2048, message = "URL must not exceed 2048 characters")
    private String URL;
}
