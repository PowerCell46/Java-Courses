package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;

public interface OrderStatusService {

    LoiOrderStatus getByListOptionItemCode(Long code);
}
