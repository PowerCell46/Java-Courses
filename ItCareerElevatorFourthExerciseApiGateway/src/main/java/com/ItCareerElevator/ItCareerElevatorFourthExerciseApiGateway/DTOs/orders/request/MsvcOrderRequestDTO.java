package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MsvcOrderRequestDTO {

    private String customerId; // Currently logged-in user

    private List<OrderItemRequestDTO> products;
}
