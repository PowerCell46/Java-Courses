package com.ItCareerElevatorSixthExercise.DTOs.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserEmailDTO {

    private String id;

    private String username;

    private String email;
}
