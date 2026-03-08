package com.ItCareerElevatorSixthExercise.DTOs.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequestDTO {

    private String username;

    private String email;

    // private String walletAddress;

    private String password;
}
