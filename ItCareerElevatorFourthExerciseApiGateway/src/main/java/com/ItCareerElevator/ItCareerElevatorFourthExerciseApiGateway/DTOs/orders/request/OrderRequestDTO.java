package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDTO {

    private List<OrderItemRequestDTO> products; // required
}
