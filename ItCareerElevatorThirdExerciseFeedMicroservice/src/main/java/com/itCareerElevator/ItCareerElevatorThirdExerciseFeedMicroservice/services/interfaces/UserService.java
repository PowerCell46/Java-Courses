package com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.services.interfaces;

import com.itCareerElevator.ItCareerElevatorThirdExerciseFeedMicroservice.entities.User;

public interface UserService {

    User getBySnowflakeId(String snowflakeId);
}
