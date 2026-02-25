package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserDepositResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWalletCryptoServiceImpl implements UserWalletService {

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public UserDepositResponseDTO processDeposit(UserDepositRequestDTO requestDTO) {
        return null;
    }
}
