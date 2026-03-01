package com.ItCareerElevatorSixthExercise.listeners;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.UserRegisteredDTO;
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
    public void handleRegisterUserMessage(UserRegisteredDTO registerDTO) {
        if (registerDTO == null || registerDTO.getId() == null)
            return;

        log.info("---> Handling registerUser with id {}.", registerDTO.getId());

        if (userRepository.findById(registerDTO.getId()).isPresent()) {
            log.info("User with id {} is already registered. Skipping...", registerDTO.getId());
            return;
        }

        var walletRequestDTO = new UserLocalWalletRequestDTO(registerDTO.getId(), BigDecimal.ZERO);
        userLocalWalletService.initializeUser(walletRequestDTO);
    }
}
