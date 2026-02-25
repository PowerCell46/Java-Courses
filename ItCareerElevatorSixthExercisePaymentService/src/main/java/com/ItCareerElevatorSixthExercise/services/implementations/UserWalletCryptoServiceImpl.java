package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserDepositResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.UserAlreadyExistsException;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWalletCryptoServiceImpl implements UserWalletService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        log.info("Persisting user with id {} to the database.", user.getId());
        return userRepository.save(user);
    }

    @Override
    public UserDepositResponseDTO initializeUser(UserDepositRequestDTO requestDTO) {
        if (userAlreadyExists(requestDTO.getUserId())) {
            throw new UserAlreadyExistsException(
                    String.format("User with id %s already exists.", requestDTO.getUserId())
            );
        }

        User user = new User(
                requestDTO.getUserId(),
                requestDTO.getAmount()
        );
        user = save(user);

        return new UserDepositResponseDTO(user.getId(), user.getBalance());
    }

    private boolean userAlreadyExists(String userId) {
        return userRepository
                .findById(userId)
                .isPresent();
    }

    @Override // ! This can throw an err if an order executes at the same time (retry until successful)
    public UserDepositResponseDTO processDeposit(UserDepositRequestDTO requestDTO) {
        User user = getById(requestDTO.getUserId());
        user.setBalance(user.getBalance().add(requestDTO.getAmount()));
        user = userRepository.save(user);

        return new UserDepositResponseDTO(user.getId(), user.getBalance());
    }

    private User getById(String userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new NoSuchUserException(String.format("No user found with id %s.", userId)));
    }
}
