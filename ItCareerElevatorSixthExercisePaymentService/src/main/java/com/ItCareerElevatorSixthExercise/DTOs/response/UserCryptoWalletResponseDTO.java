package com.ItCareerElevatorSixthExercise.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCryptoWalletResponseDTO {

    private String id; // UUID

    private String walletAddress;
}
