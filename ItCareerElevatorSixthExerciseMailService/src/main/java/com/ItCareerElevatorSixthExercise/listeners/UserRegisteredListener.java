package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.UserRegisteredDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredListener {

    private final MailService mailService;

    @KafkaListener(
            topics = "${app.kafka.topics.user-registered}",
            groupId = "${spring.kafka.user-registered.group-id}",
            containerFactory = "userRegisteredKafkaListenerContainerFactory"
    )
    public void handleUserRegisteredMessage(UserRegisteredDTO registerDTO) {
        if (registerDTO == null || registerDTO.getId() == null)
            return;

        log.info("---> Handling userRegistered with id {}.", registerDTO.getId());

        mailService.sendRegistrationMail(registerDTO);
    }
}
