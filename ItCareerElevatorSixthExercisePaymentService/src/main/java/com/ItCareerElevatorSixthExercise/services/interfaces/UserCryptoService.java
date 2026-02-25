package com.ItCareerElevatorSixthExercise.services.interfaces;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetUserWalletAddressResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import org.web3j.protocol.core.methods.response.EthBlock;

public interface UserCryptoService {

    User save(User user);

    UserResponseDTO setWalletAddress(UserRequestDTO requestDTO);

    void processTransaction(EthBlock.TransactionObject transaction);

    GetUserWalletAddressResponseDTO getWalletAddress(String id);
}
