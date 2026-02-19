package com.ItCareerElevatorSixthExercise.DTOs.order.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MsvcOrderRequestDTO {

    private String userId;

    private List<OrderItemRequestDTO> items;
}
