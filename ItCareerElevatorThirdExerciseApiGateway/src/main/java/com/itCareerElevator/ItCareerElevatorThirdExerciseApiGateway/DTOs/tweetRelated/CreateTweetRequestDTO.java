package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTweetRequestDTO {

    @NotNull
    @Pattern(regexp = "^.{3,1000}$", message = "Content must be between 3 and 1000 characters long.")
    private String content;
}
