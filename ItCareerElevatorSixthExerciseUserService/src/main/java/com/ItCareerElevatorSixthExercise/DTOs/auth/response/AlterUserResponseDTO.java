package com.ItCareerElevatorSixthExercise.DTOs.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlterUserResponseDTO {

    private String id;

    private String username;

    private String email;
}
