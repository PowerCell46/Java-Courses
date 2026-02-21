package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.CurrencyConversionService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProcessedOrderService processedOrderService;
    private final ProcessedOrderRepository processedOrderRepository;
    private final CurrencyConversionService currencyConversionService;

    @Override
    public UserResponseDTO setWalletAddress(UserRequestDTO requestDTO) {
        Optional<User> optionalUser = userRepository.findById(requestDTO.getId());

        if (optionalUser.isPresent()) {
            log.info("Updating wallet address of user with id {}.", requestDTO.getId());
            optionalUser.get().setWalletAddress(requestDTO.getWalletAddress());
            userRepository.save(optionalUser.get());

        } else {
            User user = new User(requestDTO.getId(), requestDTO.getWalletAddress());
            user = save(user);
        }

        return new UserResponseDTO(requestDTO.getId());
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with id {} to the database.", user.getId());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void processTransaction(EthBlock.TransactionObject transaction) {
        Optional<User> optionalUser = userRepository.findById(transaction.getFrom());

        if (optionalUser.isEmpty()) // ? Unknown wallet has sent us crypto
            return;

        BigDecimal paidAmountInEther = Convert.fromWei(new BigDecimal(transaction.getValue()), Convert.Unit.ETHER);
        BigDecimal paidAmountInEuros = currencyConversionService.convertEtherToEuro(paidAmountInEther);

        Optional<ProcessedOrder> optionalProcessedOrder = processedOrderService
                .findByUserIdAndApproximateTotalPrice(optionalUser.get().getId(), paidAmountInEuros);

        if (optionalProcessedOrder.isEmpty()) // ? Couldn't find such order
            return;

        optionalProcessedOrder.get().setStatus(ProcessedOrderStatus.PAID);
        processedOrderRepository.save(optionalProcessedOrder.get());

        processedOrderService
                .sendKafkaSuccessfulOrderPayment(optionalProcessedOrder.get());
    }
}
