package com.ItCareerElevatorSixthExercise.DTOs.order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Valid
    @NotNull(message = "Items must not be null.")
    @Size(min = 1, message = "Items must contain at least one entry.")
    private List<OrderItemRequestDTO> items;
}
