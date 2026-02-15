package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.entities.LoiOrderStatus;

public interface LoiOrderStatusService {

    LoiOrderStatus getByListOptionItemCode(Long code);
}
