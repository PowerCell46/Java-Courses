package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserLocalWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserLocalWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.UserAlreadyExistsException;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserLocalWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLocalWalletServiceImpl implements UserLocalWalletService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO initializeUser(UserLocalWalletRequestDTO requestDTO) {
        if (userAlreadyExists(requestDTO.getId())) {
            throw new UserAlreadyExistsException(String.format("User with id %s already exists.", requestDTO.getId()));
        }

        User user = new User(requestDTO.getId(), requestDTO.getAmount());
        user = save(user);

        return new UserResponseDTO(user.getId());
    }

    private boolean userAlreadyExists(String userId) {
        return userRepository
                .findById(userId)
                .isPresent();
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with id {} to the database.", user.getId());
        return userRepository.save(user);
    }

    @Override
    public UserLocalWalletResponseDTO processDeposit(String id, BigDecimal depositAmount) {
        User user = getById(id);

        user.setBalance(user.getBalance().add(depositAmount));
        user = userRepository.save(user);

        return new UserLocalWalletResponseDTO(user.getId(), user.getBalance());
    }

    @Override
    public UserLocalWalletResponseDTO getUserById(String id) {
        User user = getById(id);

        return new UserLocalWalletResponseDTO(user.getId(), user.getBalance());
    }

    private User getById(String userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with id %s.", userId)));
    }
}
