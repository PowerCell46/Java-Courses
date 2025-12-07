package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.tweetRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeTweetRequestDTO {

    @JsonProperty("tweetId")
    @NotNull(message = "Tweet id is required.")
    @Pattern(regexp = "^[A-Za-z0-9]{11}$", message = "Username must be exactly 11 alphanumeric characters.")
    private String tweetSnowflakeId;
}
