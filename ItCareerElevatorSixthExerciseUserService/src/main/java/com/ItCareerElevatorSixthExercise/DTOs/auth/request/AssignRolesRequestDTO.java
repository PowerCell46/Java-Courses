package com.ItCareerElevatorSixthExercise.DTOs.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AssignRolesRequestDTO {

    private String username;

    private List<String> roles;
}
