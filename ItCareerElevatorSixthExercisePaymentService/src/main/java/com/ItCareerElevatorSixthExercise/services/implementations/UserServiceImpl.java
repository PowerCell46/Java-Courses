package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO setWalletAddress(UserRequestDTO requestDTO) {
        Optional<User> optionalUser = userRepository.findById(requestDTO.getId());

        if (optionalUser.isPresent()) {
            log.info("Updating wallet address of user with id {}.", requestDTO.getId());
            optionalUser.get().setWalletAddress(requestDTO.getWalletAddress());
            userRepository.save(optionalUser.get());

        } else {
            User user = new User(requestDTO.getId(), requestDTO.getWalletAddress());
            save(user);
        }

        return new UserResponseDTO(requestDTO.getId());
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with id {} to the database.", user.getId());
        return userRepository.save(user);
    }
}
