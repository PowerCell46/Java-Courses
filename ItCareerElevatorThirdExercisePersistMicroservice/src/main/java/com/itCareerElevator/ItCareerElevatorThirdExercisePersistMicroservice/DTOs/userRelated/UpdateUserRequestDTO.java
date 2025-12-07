package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.userRelated;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserRequestDTO {

    @JsonProperty("id")
    @NotNull
    private String snowflakeId;

    // TODO: Should we allow the User to change his/hers username?

    private String firstName;

    private String lastName;

    private Boolean isMale;

    private String bio;

    private String city;

    private String country;
}
