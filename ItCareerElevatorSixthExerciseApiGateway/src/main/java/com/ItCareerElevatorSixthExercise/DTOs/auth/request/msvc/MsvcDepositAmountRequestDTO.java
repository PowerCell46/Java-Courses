package com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MsvcDepositAmountRequestDTO {

    private String id; // UUID

    private BigDecimal amount;
}
