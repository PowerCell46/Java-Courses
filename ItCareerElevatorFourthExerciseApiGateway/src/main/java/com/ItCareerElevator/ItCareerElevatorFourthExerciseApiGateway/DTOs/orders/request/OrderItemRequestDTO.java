package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "ProductId must not be null.")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "ProductId must be a valid UUID."
    )
    private String productId;

    @NotNull(message = "Quantity must not be null.")
    @Min(value = 1, message = "Quantity must be greater than 0.")
    private Integer quantity;
}
