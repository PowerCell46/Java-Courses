package com.ItCareerElevatorSixthExercise.DTOs.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MsvcGetUserWalletAddressResponseDTO {

    private String id;

    private String walletAddress;
}
