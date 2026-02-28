package com.ItCareerElevatorSixthExercise.DTOs.kafka.registerUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredDTO {

    private String id;

    private String username;

    private String email;
}
