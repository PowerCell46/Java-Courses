package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder
public class UserResponseDTO {

    private String id;

    private String username;

//    private String firstName;

//    private String lastName;

//    private Boolean isMale;

//    private String bio;

//    private String city;

//    private String country;

    private Collection<String> followers;

    // TODO: The other fields?
}
