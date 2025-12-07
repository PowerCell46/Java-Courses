package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    @JsonProperty("id")
    private String snowflakeId;

    private String username;

    private String firstName;

    private String lastName;

    private Boolean isMale;

    private String bio;

    private String city;

    private String country;

    private Collection<String> following;
}
