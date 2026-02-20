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
public class PaymentInstructionsResponse {

    private String walletAddress;

    private BigDecimal amountInEth;

    private String instructions;
}
