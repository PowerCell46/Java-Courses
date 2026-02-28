package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.registerUser.RegisterUserDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserLocalWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserLocalWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class registerUserListener {

    private final UserRepository userRepository;
    private final UserLocalWalletService userLocalWalletService;

    @KafkaListener(
            topics = "${app.kafka.topics.register-user}",
            groupId = "${spring.kafka.register-user-consumer.group-id}",
            containerFactory = "registerUserKafkaListenerContainerFactory"
    )
    public void handleRegisterUserMessage(RegisterUserDTO registerUserDTO) {
        if (registerUserDTO == null || registerUserDTO.getId() == null)
            return;

        log.info("---> Handling registerUser with id {}.", registerUserDTO.getId());

        if (userRepository.findById(registerUserDTO.getId()).isPresent()) {
            log.info("User with id {} is already registered. Skipping...", registerUserDTO.getId());
            return;
        }

        userLocalWalletService
                .initializeUser(new UserLocalWalletRequestDTO(registerUserDTO.getId(), BigDecimal.ZERO));
    }
}
