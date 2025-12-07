package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequestDTO {

    @Pattern(regexp = "^[A-Za-z]{2,50}$", message = "First name must be 2-50 letters.")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]{2,50}$", message = "Last name must be 2-50 letters.")
    private String lastName;

    @NotNull(message = "IsMale is required.")
    private Boolean isMale;

    @Pattern(regexp = "^.{3,50}$", message = "Bio must be 3-50 characters.")
    private String bio;

    @Pattern(regexp = "^[A-Za-z]{1,30}$", message = "City must be 1-30 letters.")
    private String city;

    @Pattern(regexp = "^[A-Za-z]{1,30}$", message = "Country must be 1-30 letters.")
    private String country;
}
