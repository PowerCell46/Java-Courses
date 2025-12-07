package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.tweetRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTweetRequestDTO {

    @JsonProperty("userId")
    private String userSnowflakeId;

    private String content;
}
