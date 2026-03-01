package com.ItCareerElevatorSixthExercise.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLocalWalletResponseDTO {

    private String id; // UUID

    private BigDecimal balance;
}
