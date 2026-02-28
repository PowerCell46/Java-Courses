package com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MsvcUpdateUserWalletRequestDTO {

    private String walletAddress;
}
