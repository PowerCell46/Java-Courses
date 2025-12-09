package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {

//    @JsonProperty("id")
//    private String snowflakeId;

    private String username;

    private String token;
}
