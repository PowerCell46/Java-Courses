package com.ItCareerElevatorSixthExercise.DTOs.reserveItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private String productId;

    private Integer quantity;
}
