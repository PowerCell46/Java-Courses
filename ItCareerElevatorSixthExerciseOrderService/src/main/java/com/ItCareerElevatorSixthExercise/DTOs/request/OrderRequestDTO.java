package com.ItCareerElevatorSixthExercise.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private String userId; // UUID

    private List<OrderItemRequestDTO> items;
}
