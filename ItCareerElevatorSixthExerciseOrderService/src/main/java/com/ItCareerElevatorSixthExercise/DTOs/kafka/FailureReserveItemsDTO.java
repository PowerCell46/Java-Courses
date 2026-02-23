package com.ItCareerElevatorSixthExercise.DTOs.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailureReserveItemsDTO {

    private Long orderId;

    private String reason; // NOT_IN_STOCK, (FAILED_AT_)PROCESSING
}
