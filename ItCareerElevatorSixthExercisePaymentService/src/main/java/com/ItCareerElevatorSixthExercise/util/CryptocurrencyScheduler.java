package com.ItCareerElevatorSixthExercise.util;

import com.ItCareerElevatorSixthExercise.repositories.BlockScanStateRepository;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CryptocurrencyScheduler {

    private final UserRepository userRepository;
    private final BlockScanStateRepository blockScanStateRepository;

    @Transactional
    @Scheduled(fixedDelay = 1_000 * 60 * 15) // TODO: 15 ATM, maybe should be more frequent
    public void func() { // TODO: Change name

    }
}
