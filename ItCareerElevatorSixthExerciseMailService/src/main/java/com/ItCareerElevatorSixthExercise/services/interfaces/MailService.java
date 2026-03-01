package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.UserRegisteredDTO;

public interface MailService {

    void sendRegistrationMail(UserRegisteredDTO registerDTO);
}
