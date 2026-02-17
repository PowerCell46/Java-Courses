package com.ItCareerElevatorSixthExercise.DTOs.kafka.failureReserveItems;

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

    private String reason;
}
