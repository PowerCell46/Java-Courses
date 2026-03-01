package com.ItCareerElevatorSixthExercise.DTOs.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUnsuccessfulDTO {

    private Long orderId; // snowflakeId

    private String reason; // PROCESSING, MISSING_WALLET_ADDRESS, TIMED_OUT, INSUFFICIENT_BALANCE
}
