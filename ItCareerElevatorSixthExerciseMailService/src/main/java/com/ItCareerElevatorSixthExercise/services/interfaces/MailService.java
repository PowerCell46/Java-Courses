package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.orderCompleted.OrderCompletedDTO;
import com.ItCareerElevatorSixthExercise.DTOs.userRegistered.UserRegisteredDTO;

public interface MailService {

    void sendRegistrationMail(UserRegisteredDTO registerDTO);

    void sendOrderMail(OrderCompletedDTO orderDTO);
}
