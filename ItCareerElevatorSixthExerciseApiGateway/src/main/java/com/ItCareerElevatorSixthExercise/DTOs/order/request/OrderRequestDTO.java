package com.ItCareerElevatorSixthExercise.DTOs.order.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Items must not be null.")
    @NotEmpty(message = "At least one item is required.")
    private List<OrderItemRequestDTO> items;
}
// TODO: Validations don't activate atm