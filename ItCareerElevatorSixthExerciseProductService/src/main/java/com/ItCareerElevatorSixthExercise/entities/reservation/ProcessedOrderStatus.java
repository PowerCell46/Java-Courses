package com.ItCareerElevatorSixthExercise.entities.reservation;

public enum ProcessedOrderStatus {

    PROCESSING, // 0

    INVALID_PRODUCTS, // 1

    RESERVED, // 2

    NOT_IN_STOCK, // 3

    PAID, // 4

    SENT_TO_KAFKA // 5
}
