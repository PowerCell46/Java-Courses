package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.registerUser.UserRegisteredDTO;
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
public class UserRegisteredListener {

    private final UserRepository userRepository;
    private final UserLocalWalletService userLocalWalletService;

    @KafkaListener(
            topics = "${app.kafka.topics.user-registered}",
            groupId = "${spring.kafka.user-registered-consumer.group-id}",
            containerFactory = "userRegisteredKafkaListenerContainerFactory"
    )
    public void handleRegisterUserMessage(UserRegisteredDTO userRegisteredDTO) {
        if (userRegisteredDTO == null || userRegisteredDTO.getId() == null)
            return;

        log.info("---> Handling registerUser with id {}.", userRegisteredDTO.getId());

        if (userRepository.findById(userRegisteredDTO.getId()).isPresent()) {
            log.info("User with id {} is already registered. Skipping...", userRegisteredDTO.getId());
            return;
        }

        userLocalWalletService
                .initializeUser(new UserLocalWalletRequestDTO(userRegisteredDTO.getId(), BigDecimal.ZERO));
    }
}
