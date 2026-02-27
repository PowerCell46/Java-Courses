package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import org.web3j.protocol.core.methods.response.EthBlock;

public interface UserCryptoService {

    User save(User user);

    UserWalletResponseDTO setWalletAddress(UserRequestDTO requestDTO);

    void processTransaction(EthBlock.TransactionObject transaction);

    UserWalletResponseDTO getWalletAddress(String id);
}
