package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.utils;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities.Order;

public record OrderCreatedEvent(
        String orderId,
        String customerId
) {
    public static OrderCreatedEvent from(Order order) {
        return new OrderCreatedEvent(
                order.getId(),
                order.getCustomer().getId()
        );
    }
}
