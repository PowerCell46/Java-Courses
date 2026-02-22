package com.ItCareerElevatorSixthExercise.DTOs.order.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotNull(message = "Product id is required.")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]{11}$",
            message = "Product id must be a valid snowflake ID."
    )
    private String productId;

    @NotNull(message = "Quantity is required.")
    @Positive(message = "Quantity must be a positive number.")
    private Integer quantity;
}
