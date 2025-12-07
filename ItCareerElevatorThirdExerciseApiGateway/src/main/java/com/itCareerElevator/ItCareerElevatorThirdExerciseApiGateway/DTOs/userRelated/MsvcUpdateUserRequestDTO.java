package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MsvcUpdateUserRequestDTO {

    @JsonProperty("id")
    private String snowflakeId;

    private String firstName;

    private String lastName;

    private Boolean isMale;

    private String bio;

    private String city;

    private String country;
}
