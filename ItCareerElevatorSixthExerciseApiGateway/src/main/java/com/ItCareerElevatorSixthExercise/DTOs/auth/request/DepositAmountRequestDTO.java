package com.ItCareerElevatorSixthExercise.DTOs.auth.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositAmountRequestDTO {

    @NotNull(message = "Amount must not be null.")
    @Positive(message = "Amount must be greater than 0.")
    private BigDecimal amount;
}
