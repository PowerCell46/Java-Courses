package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder
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
