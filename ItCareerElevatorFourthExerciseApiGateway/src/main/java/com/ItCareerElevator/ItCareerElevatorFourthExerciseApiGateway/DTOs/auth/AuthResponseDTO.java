package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.auth;

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
