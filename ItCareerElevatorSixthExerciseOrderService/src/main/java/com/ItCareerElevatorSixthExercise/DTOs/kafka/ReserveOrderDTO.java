package com.ItCareerElevatorSixthExercise.DTOs.kafka;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveOrderDTO {

    private Long id; // snowflakeId

    private String userId; // UUID

    private String userEmail;

    private List<ReserveOrderItemDTO> orderItems;
}
