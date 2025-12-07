package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.tweetRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class TweetResponseDTO {

    @JsonProperty("id")
    private String snowflakeId;

    private String content;

    private String createdBy;

    private Collection<String> likedBy;
}
