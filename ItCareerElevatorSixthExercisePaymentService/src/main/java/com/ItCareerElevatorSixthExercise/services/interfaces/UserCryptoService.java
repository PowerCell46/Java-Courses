package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserCryptoWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserCryptoWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import org.web3j.protocol.core.methods.response.EthBlock;

public interface UserCryptoService {

    User save(User user);

    UserCryptoWalletResponseDTO setWalletAddress(UserCryptoWalletRequestDTO requestDTO);

    void processTransaction(EthBlock.TransactionObject transaction);

    UserCryptoWalletResponseDTO getWalletAddress(String id);
}
