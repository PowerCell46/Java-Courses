package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.DepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.DepositAmountResponseDTO;

public interface PaymentService {

    void initializeCryptoWalletAddress(String id, String walletAddress);

    void updateCryptoWalletAddress(String id, String walletAddress);

    DepositAmountResponseDTO depositAmount(DepositAmountRequestDTO requestDTO);
}
