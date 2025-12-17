package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderRequestDTO {

    @Valid
    @NotNull(message = "Products must not be null.")
    @Size(min = 1, message = "Products must contain at least one item.")
    private List<OrderItemRequestDTO> products;
}
