package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeTweetRequestDTO {

    @JsonProperty("userId")
    private String userSnowflakeId;

    @JsonProperty("tweetId")
    private String tweetSnowflakeId;
}
