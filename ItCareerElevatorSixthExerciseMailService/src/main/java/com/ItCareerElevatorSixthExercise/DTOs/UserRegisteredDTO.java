package com.ItCareerElevatorSixthExercise.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredDTO {

    private String id; // UUID

    private String username;

    private String email;
}
