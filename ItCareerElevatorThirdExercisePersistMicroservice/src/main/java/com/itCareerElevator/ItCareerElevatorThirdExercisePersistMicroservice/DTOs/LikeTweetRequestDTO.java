package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeTweetRequestDTO {

    @JsonProperty("userId")
    private String userSnowflakeId;

    @JsonProperty("tweetId")
    private String tweetSnowflakeId;
}
