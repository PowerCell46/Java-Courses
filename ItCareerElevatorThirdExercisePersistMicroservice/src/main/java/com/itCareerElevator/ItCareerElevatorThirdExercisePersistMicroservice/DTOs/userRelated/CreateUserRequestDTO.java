package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDTO {

    @JsonProperty("id")
    private String snowflakeId;

    private String username;
}
