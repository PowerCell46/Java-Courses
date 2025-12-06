package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTweetRequestDTO {

    @JsonProperty("userId")
    private String userSnowflakeId;

    private String content;
}
